package com.edson.customer.entity;
/**
* @author  Edson Filho
* @version 1.0
* @since   2020-09-14 
*/
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "AdressEntity")
@Table(name = "TB_ADRESS")
public class AdressEntity  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ADRESS_ID")
	private Long id;
	
	@Column(name ="STREET_NAME",nullable = true)
	private String streetName;
	
	@Column(name ="ADRESS_NUMBER",nullable = true)
	private int AdressNumber;
	
	@Column(name ="COMPLEMENT",nullable = true)
	private String complement;
	
	@Column(name ="CEP",nullable = true)
	private int cep;
	
	@Column(name ="NEIGHBORHOOD",nullable = true)
	private String neighborhood;
	
	@Column(name ="CITY",nullable = true)
	private String city;
	
	@Column(name ="STATE",nullable = true)
	private String state;
	
	@Column(name ="COUNTRY",nullable = true)
	private String country;
}
