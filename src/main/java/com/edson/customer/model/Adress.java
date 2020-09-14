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
public class Adress  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("STREET_NAME")
	private String streetName;
	@JsonProperty("ADRESS_NUMBER")
	private int AdressNumber;
	@JsonProperty("COMPLEMENT")
	private String complement;
	@JsonProperty("CEP")
	private int cep;
	@JsonProperty("NEIGHBORHOOD")
	private String neighborhood;
	@JsonProperty("CITY")
	private String city;
	@JsonProperty("STATE")
	private String state;
	@JsonProperty("COUNTRY")
	private String country;
}
