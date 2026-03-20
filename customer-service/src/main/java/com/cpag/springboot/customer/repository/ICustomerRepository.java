package com.cpag.springboot.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpag.springboot.customer.entity.Customer;

public interface ICustomerRepository extends JpaRepository<Customer,String>{
	Customer findByCustomerId(String customerId);
	
//	@Query("select c from Customer c where c.address= :n")
//	public List<Customer> findCustomerByCity(@Param("n") String location);
}
