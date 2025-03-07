package pet.stores.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pet.stores.entity.Customer;



public interface CustomerDao extends JpaRepository<Customer, Long>{

}