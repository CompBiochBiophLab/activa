package com.o2hlink.activ8.common.entity;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation class to map a set of properties.
 * @author Miguel Angel Hernandez
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MappingProperties {
	/**
	 * The set of properties to map
	 **/
	public MappingProperty[] value();
}
