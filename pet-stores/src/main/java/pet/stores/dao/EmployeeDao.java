package pet.stores.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pet.stores.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
