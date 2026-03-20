package com.cpag.springboot.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpag.springboot.customer.dto.Customerdto;
import com.cpag.springboot.customer.entity.Customer;
import com.cpag.springboot.customer.exception.CustomerNotFoundException;
import com.cpag.springboot.customer.repository.ICustomerRepository;
import com.cpag.springboot.customer.service.CustomerService.ICustomerService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService{

	@Autowired
	ICustomerRepository cr;
	
	@Override
	public Customer findByCustomerId(Customer cust) throws CustomerNotFoundException{
		Customer customer = cr.findById(cust.getCustomerId()).orElse(null);
		if(customer==null) {
			throw new CustomerNotFoundException(cust.getCustomerId()+" id don't exist");
		}
		else {
			return customer;
		}
	}
	@Override
	public Customer removeCustomer(Customer cust) {
		cr.delete(cust);
		return cust;
	}
	@Override
	public List<Customer> viewAllCustomer() throws CustomerNotFoundException{
		return cr.findAll();
	}
	
	@Override
	public Customer addCustomer(Customerdto custdto) {
		
	}
	
	
}
