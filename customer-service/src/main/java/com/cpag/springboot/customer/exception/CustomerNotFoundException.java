package com.cpag.springboot.customer.exception;


public class CustomerNotFoundException extends Exception {
	public CustomerNotFoundException(String s) {
		super(s);
	}
}