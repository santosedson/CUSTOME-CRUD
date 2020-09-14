package com.edson.customer.model;
/**
* @author  Edson Filho
* @version 1.0
* @since   2020-09-14 
*/
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Customer implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("NAME")
	private String name;
	@JsonProperty("CPF")
	private String cpf;
	@JsonProperty("ADRESS")
	private Adress adress;
	
	public Customer(String name, String cpf, Adress adress) {
		super();
		this.name = name;
		this.cpf = cpf;
		this.adress = adress;
	}

	public Customer() {
		super();
	}
	
	

}
