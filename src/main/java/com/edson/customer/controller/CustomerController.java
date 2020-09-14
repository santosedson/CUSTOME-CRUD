package com.edson.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edson.customer.commons.ApplicationMessages;
import com.edson.customer.model.Customer;
import com.edson.customer.service.CustomerService;
/**
* @author  Edson Filho
* @version 1.0
* @since   2020-09-14 
*/
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
	
	@Autowired
	CustomerService service;

	@PostMapping("/register")
	public ResponseEntity<?> registerCustomer(@RequestBody (required=false)  Customer customer) {
		if(customer!=null) {
			String response= service.checkAndInsertCustomer(customer);
			return ResponseEntity.accepted().body(response);
		}else {
			return ResponseEntity.badRequest().body(ApplicationMessages.BAD_REQUEST.getCode());
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateCustomer(@RequestBody (required=false) Customer customer) {
		if(customer!=null) {
			String response= service.checkAndUpdateCustomer(customer);
			return ResponseEntity.accepted().body(response);
		}else {
			return ResponseEntity.badRequest().body(ApplicationMessages.BAD_REQUEST.getCode());
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteCustomer(@RequestParam(value = "cpf", required = false) String cpf) {
		String response= service.checkAndDelete(cpf);
		return ResponseEntity.accepted().body(response);
	}

	@GetMapping("/findAll")
	public ResponseEntity<?> findAllCustomers() {
		List<Customer> customerList=service.getAllCustomers();
		return ResponseEntity.accepted().body(customerList);
	}

	@GetMapping("/findBy")
	public ResponseEntity<?> findCustomerBy(@RequestParam(value = "cpf", required = false) String cpf) {
		return ResponseEntity.accepted().body(service.checkAndFindByCpf(cpf));
	}

}
