package com.o2hlink.activ8.common.entity;

import java.util.Map;

/**
 * A mapping function is used to do prior operations before mapping
 * @author Miguel Angel Hernandez
 **/
public interface MappingFunction<I, O> {
	/**
	 * Converts the input value to the output value
	 * @param	I The input value
	 * @param	args Additional arguments
	 * @return	The output value
	 **/
	public O map(I value, Map<String, Object> args);
}
