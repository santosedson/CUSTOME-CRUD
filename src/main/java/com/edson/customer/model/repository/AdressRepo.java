package com.edson.customer.model.repository;
/**
* @author  Edson Filho
* @version 1.0
* @since   2020-09-14 
*/
import org.springframework.data.jpa.repository.JpaRepository;

import com.edson.customer.entity.AdressEntity;

public interface AdressRepo extends JpaRepository<AdressEntity, Long> {

}
