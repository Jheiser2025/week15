package pet.stores.controller.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import pet.stores.entity.Customer;
import pet.stores.entity.Employee;
import pet.stores.entity.PetStores;

@Data
@NoArgsConstructor
public class PetStoresData{

	private Long petStoreId;
	private  String petStoreName;
	private  String petStoreAddress;
	private  String petStoreCity;
	private  String petStoreState;
	private  String petStoreZip;
	private  String petStorePhone;
	private Set<PetStoreCustomer> customers = new HashSet<>();
	private Set<PetStoreEmployee> employees = new HashSet<>();

	public PetStoresData(PetStores petStores) {
	petStoreId = petStores.getPetStoreId();
	petStoreName = petStores.getPetStoreName();
	petStoreAddress= petStores.getPetStoreAddress();
	petStoreCity= petStores.getPetStoreCity();
	petStoreState = petStores.getPetStoreState();
	petStoreZip = petStores.getPetStoreZip();
	petStorePhone = petStores.getPetStorePhone();
	for (Customer customer : petStores.getCustomers()) {
		customers.add(new PetStoreCustomer(customer));
	}
	for (Employee employee : petStores.getEmployees()) {
		employees.add(new PetStoreEmployee(employee));
	}
	}
	@Data
	@NoArgsConstructor
	public static class PetStoreCustomer {
		private Long customerId;
		private String customerFirstName;
		private String customerLastName;
		private String customerEmail;
public PetStoreCustomer(Customer customer) {
	customerId = customer.getCustomerId();
	customerFirstName = customer.getCustomerFirstName();
	customerLastName = customer.getCustomerLastName();
	customerEmail = customer.getCustomerEmail();
}
}
	@Data
	@NoArgsConstructor
	public static class PetStoreEmployee {
	
	private Long employeeID;
	private String employeeFirstName;
	private String employeeLastName;
	private String employeePhoneNumber;
	private String employeeJobTitle;
	private String employeeEmail;
	public PetStoreEmployee(Employee employee) {
		employeeID = employee.getEmployeeID();
		employeeFirstName = employee.getEmployeeFirstName();
		employeeLastName = employee.getEmployeeLastName();
		employeePhoneNumber = employee.getEmployeePhoneNumber();
		employeeJobTitle  = employee.getEmployeeJobTitle();
		employeeEmail  = employee.getEmployeeEmail();
		
}

}
}
