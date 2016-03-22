package com.o2hlink.activ8.common.entity;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation class to map a property over another property.
 * @author Miguel Angel Hernandez
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MappingProperty {
	/**
	 * The name of the mapping property. By default the mapping property is the one indicated by this annotation, but in some cases it is desired to map a property of the resulting value. 
	 **/
	public String name() default "";
	/**
	 * A mapping function receives its input value from {@link MappingProperty#name()} and its return value is injected into {@link MappingProperty#maps()}. 
	 **/
	public Class<? extends MappingFunction<?, ?>>[] function() default {};
	/**
	 * The name of the property in the destination class. 
	 **/
	public String maps() default "";
}
