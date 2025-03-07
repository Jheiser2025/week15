package pet.stores.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.stores.controller.model.PetStoresData;
import pet.stores.controller.model.PetStoresData.PetStoreCustomer;
import pet.stores.controller.model.PetStoresData.PetStoreEmployee;
import pet.stores.dao.CustomerDao;
import pet.stores.dao.EmployeeDao;
import pet.stores.dao.PetStoreDao;
import pet.stores.entity.Customer;
import pet.stores.entity.Employee;
import pet.stores.entity.PetStores;

@Service
public class PetStoreService {
@Autowired
	private PetStoreDao petStoreDao;
@Transactional(readOnly = false)
public PetStoresData savePetStores(PetStoresData petStoresData) {
	Long petStoreId = petStoresData.getPetStoreId();
	PetStores petStores= findOrCreatePetStores(petStoreId);
	
	copyPetStoresFields(petStores, petStoresData);
return new PetStoresData(petStoreDao.save(petStores));
}
	
private void copyPetStoresFields(PetStores petStores, PetStoresData petStoresData) {
	petStores.setPetStoreAddress(petStoresData.getPetStoreAddress());
	petStores.setPetStoreCity(petStoresData.getPetStoreCity());
	petStores.setPetStoreId(petStoresData.getPetStoreId());
	petStores.setPetStoreName(petStoresData.getPetStoreName());
	petStores.setPetStorePhone(petStoresData.getPetStorePhone());
	petStores.setPetStoreState(petStoresData.getPetStoreState());
	petStores.setPetStoreZip(petStoresData.getPetStoreZip());
	
}
private PetStores findOrCreatePetStores(Long petStoreId) {
if (Objects.isNull(petStoreId)) {
		return new PetStores();
}
else {
return findPetStoreById(petStoreId);
}
}

private PetStores findPetStoreById(Long petStoreId) {
	return petStoreDao.findById(petStoreId).orElseThrow(()-> new NoSuchElementException ("Pet Store with ID = "+ petStoreId + " was not found."));

}


@Autowired
 private EmployeeDao employeeDao;
@Transactional(readOnly=false)
public PetStoreEmployee saveEmployee(Long petStoresId, PetStoreEmployee petStoreEmployee) {
    PetStores petStores = findPetStoreById(petStoresId);
    Long employeeId = petStoreEmployee.getEmployeeID();
    Employee employee = findOrCreateEmployeeById(petStoresId, employeeId);

    copyEmployeeFields(employee, petStoreEmployee);
    employee.setPetStores(petStores);
    petStores.getEmployees().add(employee);
    Employee dbEmployee =  employeeDao.save(employee);
    return new PetStoreEmployee(dbEmployee);
}

public Employee findEmployeeById(Long petStoresId, Long employeeId) {
    Employee employee = employeeDao.findById(employeeId)
            .orElseThrow(() -> new NoSuchElementException("Employee not found"));
    if (employee.getPetStores().getPetStoreId() != petStoresId) {
        throw new IllegalArgumentException("Employee does not belong to the given pet store");
    }
    return employee;
} 
    private Employee findOrCreateEmployeeById(Long petStoreId, Long employeeId) {
      if(Objects.isNull(employeeId)) {
    	  return new Employee();
      }
      return findEmployeeById(petStoreId,employeeId);
    }

private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoresEmployee) {

    employee.setEmployeeFirstName(petStoresEmployee.getEmployeeFirstName());
    employee.setEmployeeLastName(petStoresEmployee.getEmployeeLastName());
    employee.setEmployeeID(petStoresEmployee.getEmployeeID());
    employee.setEmployeeJobTitle(petStoresEmployee.getEmployeeJobTitle());
    employee.setEmployeePhoneNumber(petStoresEmployee.getEmployeePhoneNumber());
    employee.setEmployeeEmail(petStoresEmployee.getEmployeeEmail());


}
@Autowired
private CustomerDao customerDao;

@Transactional(readOnly=false)
private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
customer.setCustomerId(petStoreCustomer.getCustomerId());
customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());

}

private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
	if(Objects.isNull(customerId)) {
		return new Customer();
	}
	return findCustomerById(petStoreId,customerId);
}
private Customer findCustomerById(Long petStoreId, Long customerId) {
Customer customer = customerDao.findById(customerId).orElseThrow(()-> new NoSuchElementException ("Customer with ID = "+ customerId + " was not found."));
boolean found = false;
for (PetStores petStores : customer.getPetStores()) {
	if(petStores.getPetStoreId() == petStoreId) {
		found = true;
		break;
	}
	}
	if (!found) {
		throw new IllegalArgumentException("Customer ID "+ customerId + " is not a member for pet store with id " + petStoreId);
	}
return customer;
}


@Transactional(readOnly=false)
public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoresCustomer) {
	PetStores petStore = findPetStoreById(petStoreId);
	Long customerId = petStoresCustomer.getCustomerId();
	Customer customer = findOrCreateCustomer(petStoreId,customerId);
	copyCustomerFields(customer,petStoresCustomer);
	  petStore.getCustomers().add(customer);
	  customer.getPetStores().add(petStore);
	Customer dbCustomer = customerDao.save(customer);
	
	return new PetStoreCustomer(dbCustomer);
}
@Transactional(readOnly = true)
public List<PetStoresData> retrieveAllPetStores(){
	List<PetStores> petStores = petStoreDao.findAll();
	List <PetStoresData> result = new LinkedList<>();
	for(PetStores petStore : petStores) {
		PetStoresData psd = new PetStoresData(petStore);
		psd.getCustomers().clear();
		psd.getEmployees().clear();
		
		result.add(psd);
	}
	return result;
}
@Transactional(readOnly =true)
public PetStoresData retrievePetStoreById(Long petStoreId) {
return new PetStoresData(findPetStoreById(petStoreId));
}

@Transactional(readOnly = false)
public void deletePetStoreById(Long petStoreId) {
	PetStores petStores = findPetStoreById(petStoreId);
	petStoreDao.delete(petStores);
}



}