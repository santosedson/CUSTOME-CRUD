package com.edson.customer.service.util;
/**
* @author  Edson Filho
* @version 1.0
* @since   2020-09-14 
*/
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

@Component
public class DozerConverter {
	private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

	/**
     * This function will map a destination object based in an object origin.
     * @param Object origin
     * @param Class destination
     * @return Object destination mapped
     */
	public static <O, D> D parseObject(O origin, Class<D> destination) {
		return mapper.map(origin, destination);
	}
	
	/**
     * This function will map a list of destination object based in a list of object origin.
     * @param List of Object origin
     * @param Class destination
     * @return List of Object destination mapped
     */
	public static <O, D> List<D> parseListObject(List<O> origin, Class<D> destination) {
		List<D> destinationObjects = new ArrayList<D>();
		for (Object o : origin) {
			destinationObjects.add(mapper.map(o, destination));
			
		}
		return destinationObjects;
	}
}
