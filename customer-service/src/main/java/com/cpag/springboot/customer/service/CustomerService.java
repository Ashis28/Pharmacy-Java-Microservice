package com.cpag.springboot.customer.service;

import java.util.List;

import com.cpag.springboot.customer.dto.Customerdto;
import com.cpag.springboot.customer.entity.Customer;
import com.cpag.springboot.customer.exception.CustomerNotFoundException;

public interface CustomerService {

public interface ICustomerService {

	
	public Customer findByCustomerId(Customer cust) throws CustomerNotFoundException;
	
	public Customer removeCustomer(Customer cust);
	public List<Customer> viewAllCustomer() throws CustomerNotFoundException;

	public Customer addCustomer(Customerdto custdto);
	public List<Customer> findCustomerByCity(String location) throws CustomerNotFoundException;
}

}
