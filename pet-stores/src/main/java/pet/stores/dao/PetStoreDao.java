package pet.stores.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pet.stores.entity.PetStores;

public interface PetStoreDao extends JpaRepository<PetStores, Long>{
	
}