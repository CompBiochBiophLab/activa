package com.o2hlink.activ8.common.entity;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation class to map a class over another class. This class can be useful when mapping a persistent class to a DTO and viceversa.
 * @author Miguel Angel Hernandez
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MappingClass {
	/**
	 * The destination class of the mapping.
	 **/
	public Class<?> type() default Object.class;
	/**
	 * The name of the destination class of the mapping.
	 **/
	public String name() default "";
}
