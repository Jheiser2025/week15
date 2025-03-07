package pet.stores.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import pet.stores.controller.model.PetStoresData;
import pet.stores.controller.model.PetStoresData.PetStoreCustomer;
import pet.stores.controller.model.PetStoresData.PetStoreEmployee;
import pet.stores.service.PetStoreService;

@RestController
@RequestMapping("/pet_stores")
@Slf4j
public class PetStoreController {
	@Autowired
	private PetStoreService petStoreService;
	
@PostMapping
@ResponseStatus(code=HttpStatus.CREATED)
public PetStoresData insertPetStore (@RequestBody PetStoresData petStoresData) {
log.info("Creating pet store {}",petStoresData);
	return petStoreService.savePetStores(petStoresData);	
	}
@PutMapping("/{petStoreId}")
public PetStoresData updatePetStore(@PathVariable Long petStoreId, @RequestBody PetStoresData 
		petStoresData) {
	petStoresData.setPetStoreId(petStoreId);
	log.info("Updating pet store {}", petStoresData);
	return petStoreService.savePetStores(petStoresData);
}
	@PostMapping("/{petStoreId}/employee")
	@ResponseStatus(code=HttpStatus.CREATED)
	public PetStoreEmployee addEmployeeToPetStore(@PathVariable Long petStoreId,@RequestBody PetStoreEmployee petStoreEmployee) {
		log.info("Adding employee {} to pet store with ID={}", petStoreEmployee,petStoreId);
		return petStoreService.saveEmployee(petStoreId,petStoreEmployee);
}
	@PostMapping("/{petStoreId}/customer")
	@ResponseStatus(code=HttpStatus.CREATED)
	public PetStoreCustomer addCustomerToPetStore(@PathVariable Long petStoreId,@RequestBody PetStoreCustomer petStoreCustomer) {
		log.info("Adding customer {} to pet store with ID={}", petStoreCustomer,petStoreId);
		return petStoreService.saveCustomer(petStoreId,petStoreCustomer);
}
	@GetMapping
	public List<PetStoresData> retrieveAllPetStores(){
		log.info("Retrieve all of the pet stores please");
		return petStoreService.retrieveAllPetStores();
}
	@GetMapping("/{petStoreId}")
	public PetStoresData retrievePetStoreById(@PathVariable Long petStoreId) {
		log.info("Please retrieve the following pet store Id{}=", petStoreId);
		return petStoreService.retrievePetStoreById(petStoreId);
	}
@DeleteMapping("/{petStoreId}")
public Map<String, String> deletePetStoreById(@PathVariable Long petStoreId) {
	log.info("Deleting pet store with id{}", petStoreId);
	petStoreService.deletePetStoreById(petStoreId);
	 return Map.of("message", "Pet Store with ID = " + petStoreId + "deleted");
}
	
}

