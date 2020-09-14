package com.edson.customer.entity;
/**
* @author  Edson Filho
* @version 1.0
* @since   2020-09-14 
*/
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "CustomerEntity")
@Table(name = "TB_CUSTOMER")
public class CustomerEntity implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CUSTOMER_ID")
	private Long id;
	
	@Column(name ="NAME",nullable = false)
	private String name;
	
	@Column(name ="CPF",nullable = false)
	private String cpf;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ADRESS_ID_FK",foreignKey = @javax.persistence.ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
	private AdressEntity adress;

}
