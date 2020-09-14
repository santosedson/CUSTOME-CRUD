package com.edson.customer.commons;
/**
* @author  Edson Filho
* @version 1.0
* @since   2020-09-14 
*/
public enum ApplicationMessages {
	
	INVALID_CPF("Invalid CPF! Please, check if informed CPF is:  A real CPF, has size 11 and is composed only numbers."),
	INVALID_NAME("Invalid Name! Please, input name having at least 2 characters and using only letters (A to Z)..  Valid names exemple: Edson F , joão pedro, DANIELA A SILVA"),
	CPF_ALREADY_EXISTS("The informed CPF is already registered"),
	CPF_NOT_FOUND("Invalid operation! The informed CPF is not found on database"),
	INSERT_SUCCESS("Customer was registered with success"),
	INSERT_FAIL("An error occurred while application tried to insert customer"),
	UPDATE_SUCCESS("Customer was updated with success"),
	UPDATE_FAIL("An error occurred while application tried to update customer"),
	DELETE_SUCCESS("Customer was removed with success"),
	BAD_REQUEST("Error! Customer is null. Please, check if Json format is correct");
	
	private String code;

	private ApplicationMessages(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
}
