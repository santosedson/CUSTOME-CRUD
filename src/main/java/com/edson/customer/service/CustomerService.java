package com.edson.customer.service;
/**
* @author  Edson Filho
* @version 1.0
* @since   2020-09-14 
*/
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edson.customer.commons.ApplicationMessages;
import com.edson.customer.entity.CustomerEntity;
import com.edson.customer.model.Customer;
import com.edson.customer.model.repository.CustomerRepo;
import com.edson.customer.service.util.DozerConverter;

@Service
public class CustomerService {

	@Autowired
	DozerConverter dozerConverter;

	@Autowired
	CustomerRepo repository;

	@Autowired
	CustomerValidations validations;

	/**
     * This function will prepare the customer to insert checking it before insert.
     * @param Customer object -> Json with the same structure of Customer
     * @return String with success message or error message
     */
	public String checkAndInsertCustomer(Customer customer) {
		String msg = validations.isAvalidInsert(customer);
		if (msg == null) {
			CustomerEntity entity = converterDtoToEntity(customer);
			if (insertCustomer(entity)) {
				return ApplicationMessages.INSERT_SUCCESS.getCode();
			} else {
				return ApplicationMessages.INSERT_FAIL.getCode();
			}
		} else {
			return msg;
		}
	}

	/**
     * This function will prepare the customer to update checking it before update.
     * @param Customer object -> Json with the same structure of Customer
     * @return String with success message or error message
     */
	public String checkAndUpdateCustomer(Customer customer) {
		String msg = validations.isAvalidUpdate(customer);
		if (msg == null) {
			CustomerEntity entity = converterDtoToEntity(customer);
			if (updateCustomer(entity)) {
				return ApplicationMessages.UPDATE_SUCCESS.getCode();
			} else {
				return ApplicationMessages.UPDATE_FAIL.getCode();
			}
		} else {
			return msg;
		}
	}
	
	/**
     * This function will prepare the customer to delete checking it before delete.
     * @param String -> cpf number of customer
     * @return String with success message or error message
     */
	public String checkAndDelete(String cpf) {
		String msg = validations.isAvalidDelete(cpf);
		if (msg == null) {
			removeCustomer(cpf);
			return ApplicationMessages.DELETE_SUCCESS.getCode();
		} else {
			return msg;
		}
	}

	/**
     * This function will try to find customer by cpf checking cpf format before try to find.
     * @param String -> cpf number of customer
     * @return Customer Object or error message
     */
	public Object checkAndFindByCpf(String cpf) {
		String msg =validations.isAValidSearchByCpf(cpf);
		CustomerEntity customerEntity;
		if (msg == null) {
			customerEntity=getCustomerByCpf(cpf);
			if(customerEntity!=null) {
				return converterEntityToDto(customerEntity); 
			}else {
				return ApplicationMessages.CPF_NOT_FOUND.getCode(); 
			}
			
		}else {
			return msg;
		}
	}
	
	
	/**
     * This function will insert a new customer.
     * @param CustomerEntity
     * @return true (success)or false (fail)
     */
	public boolean insertCustomer(CustomerEntity entity) {
		Long id = null;
		boolean sucess = false;
		if (entity != null) {
			id = repository.save(entity).getId();
		}
		if (id != null) {
			sucess = true;
		}
		return sucess;
	}

	/**
     * This function will update a customer.
     * @param CustomerEntity
     * @return true (success)or false (fail)
     */
	public boolean updateCustomer(CustomerEntity entity) {
		entity.setId(getCustomerByCpf(entity.getCpf()).getId());
		boolean sucess = false;
		if (entity != null) {
			repository.save(entity).getId();
			sucess = true;
		}
		return sucess;
	}

	/**
     * This function will remove a customer.
     * @param String cpf
     */
	public void removeCustomer(String cpf) {
		repository.deleteById(getCustomerByCpf(cpf).getId());
	}
	
	/**
     * This function will find a customer by cpf.
     * @param String cpf
     * @return CustomerEntity
     */
	public CustomerEntity getCustomerByCpf(String cpf) {
		return repository.findByCpf(cpf);
	}

	/**
     * This function will find all customers.
     * @return List of CustomerEntity
     */
	public List<Customer> getAllCustomers() {
		return converterEntityListToDto(repository.findAll());
	}

	/**
     * This function converts Customer (DTO) to CustomerEntity (Entity Database)
     * @param Customer
     * @return CustomerEntity
     */
	private CustomerEntity converterDtoToEntity(Customer customer) {
		return DozerConverter.parseObject(customer, CustomerEntity.class);
	}
	
	/**
     * This function converts CustomerEntity (Entity Database) to Customer (DTO) 
     * @param CustomerEntity
     * @return Customer
     */
	private Customer converterEntityToDto(CustomerEntity customerEntity) {
		return DozerConverter.parseObject(customerEntity, Customer.class);
	}
	
	/**
     * This function converts  a list of CustomerEntity (Entity Database) to List of Customer (DTO) 
     * @param List of CustomerEntity
     * @return List of Customer
     */
	private List<Customer> converterEntityListToDto(List<CustomerEntity> customerEntityList) {
		return DozerConverter.parseListObject(customerEntityList, Customer.class);
	}
	

}
