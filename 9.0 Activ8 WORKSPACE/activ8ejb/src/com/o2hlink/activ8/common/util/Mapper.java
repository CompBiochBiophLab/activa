package com.o2hlink.activ8.common.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingFunction;
import com.o2hlink.activ8.common.entity.MappingProperties;
import com.o2hlink.activ8.common.entity.MappingProperty;

/**
 * A mapper using annotations
 * @author Miguel Angel Hernandez
 **/
final public class Mapper {
//FIELDS
	/**
	 * 
	 **/
	private static final int maxdepth = 10;
	/**
	 * @author Miguel Angel Hernandez
	 **/
	final private static class MappingProcess {
	//FIELDS
		/**
		 * 
		 **/
		private Map<String, ? extends Object> args = null;
		/**
		 * 
		 **/
		private Hashtable<Object, Object> mapped = new Hashtable<Object, Object>();
	//METHODS
		/**
		 * 
		 **/
		public Object process(final Object source){
			this.args = new HashMap<String, Object>();
			return process(source, 0);
		}
		/**
		 * 
		 **/
		public Object process(final Object source, Map<String, ? extends Object> args){
			this.args = args;
			return process(source, 0);
		}
		/**
		 * 
		 **/
		private Object get(final Object object, final Method method, final String propertyPath){
			Object value;
			try {
				value = method.invoke(object);
			} catch (Exception e) {
				System.err.println("Failed to get value from "+object.getClass().getName()+" "+propertyPath+":"+e.getMessage());
				return null;
			} 
			if (!propertyPath.equals("")){
				String[] properties = propertyPath.split("\\.");
				for (int i=0; value!=null && i<properties.length; ++i){
					String propertyName = properties[i].substring(0, 1).toUpperCase() + properties[i].substring(1);
					Method getter;
					try{
						getter = value.getClass().getMethod("get" + propertyName);
					}
					catch (Exception e){
						try{
							getter = value.getClass().getMethod("is" + propertyName);
						}
						catch (Exception e0){
							System.err.println("No property "+object.getClass().getName()+"."+propertyName+" found :"+e.getMessage());
							return null;
						}
					}
					try {
						value = getter.invoke(value);
					} catch (Exception e) {
						System.err.println("Failed to get value from "+object.getClass().getName()+" "+propertyPath+":"+e.getMessage());
						return null;
					} 
				}
			}
			return value;
		}
		/**
		 * 
		 **/
		private void set(final Object object, final String propertyPath, final Object value){
			Object current = object;
			String[] properties = propertyPath.split("\\.");
			for (int i=0; i<properties.length; ++i){
				String propertyName = properties[i].substring(0, 1).toUpperCase() + properties[i].substring(1);
				Method getter, setter;
				try{
					getter = current.getClass().getMethod("get" + propertyName);
				}
				catch (Exception e){
					try{
						getter = current.getClass().getMethod("is" + propertyName);
					}
					catch (Exception e0){
						System.err.println("No property "+object.getClass().getName()+"."+propertyName+" found :"+e.getMessage());
						return;
					}
				}
				try {
					if (i==properties.length-1){
						setter = current.getClass().getMethod("set" + propertyName, getter.getReturnType());
						setter.invoke(current, value);
						return;
					}
					Object buffer = getter.invoke(current);
					if (buffer==null) {
						setter = current.getClass().getMethod("set" + propertyName, getter.getReturnType());
						setter.invoke(current, buffer = getter.getReturnType().newInstance());
					}
					current = buffer;						
				} catch (Exception e) {
					System.err.println("Failed to set value of "+object.getClass().getName()+" "+propertyPath);
				} 
			}
		}
		/**
		 * 
		 **/
		@SuppressWarnings("unchecked")
		private void process(final Object source, Method method, MappingProperty mappingProperty, final Object destination, int depth){
			Object propertyValue = get(source, method, mappingProperty.name());
			if (mappingProperty.function().length>0)
				try {
					Class<? extends MappingFunction> function = mappingProperty.function()[0];
					propertyValue = function.newInstance().map(propertyValue, args);
				}
				catch (Exception e){
					System.err.println("Exception when calling mapping function at method "+method.getName()+" "+e.getMessage());
					return;
				}					
			String maps = mappingProperty.maps().equals("")? 
						 	(mappingProperty.name().equals("")? 
						 			(method.getName().startsWith("get")?
						 					method.getName().substring(3): 
						 					method.getName().substring(2)): 
						 			mappingProperty.name()): 
						 	mappingProperty.maps();
			set(destination, maps, process(propertyValue,depth+1));			
		}
		/**
		 * 
		 **/
		@SuppressWarnings("unchecked")
		private Object process(final Object source, int depth){
			if (depth > Mapper.maxdepth)
				return null;
			if (source==null)
				return null;
			//PRIMITIVE
			if (source instanceof Number  || source instanceof Boolean || source instanceof Character)
				return source;
			if(source instanceof String)
				return new String((String)source);
			if(source instanceof Date)
				return ((Date)source).clone();
			//ARRAY
			if(source instanceof List){
				ArrayList<Object> array = new ArrayList<Object>();
				for (Object o:(List)source) 
					array.add(process(o, depth+1));
				return array;
			}
			//ENUM OR OBJECT
			Class<?> clazz = map(source.getClass());
			if (clazz==null)
				return source;
			//ENUM
			if (source instanceof Enum)
				try {
				    return clazz.getMethod("valueOf", String.class).invoke(clazz, source.toString());
				} catch (Exception e) {
					System.err.println("Destination enum "+clazz.getName()+" does not have constant "+source.toString());
					return null;
				} 
			//OBJECT
			if (mapped.get(source)!=null)
				return mapped.get(source);
			Object destination;
			try {
				destination = clazz.newInstance();
			} catch (Exception e) {
				System.err.println("Error when trying to instantiate class "+clazz.getName());
				return null;
			} 
			mapped.put(source, destination);
			for (Method method:source.getClass().getMethods()){
				MappingProperty mappingProperty = method.getAnnotation(MappingProperty.class);
				if (mappingProperty!=null)
					process(source, method, mappingProperty, destination, depth);
				MappingProperties mappingProperties = method.getAnnotation(MappingProperties.class);
				if (mappingProperties!=null)
					for (MappingProperty property:mappingProperties.value())
						process(source, method, property, destination, depth);
			}
			return destination;
		}
	}
	/**
	 * 
	 **/
	@SuppressWarnings("unchecked")
	final public static <A>A map(final Object source){
		return (A) (new MappingProcess()).process(source);
	}
	/**
	 * 
	 **/
	@SuppressWarnings({ "unchecked"})
	final public static <A>A map(final Object source, Map<String, ? extends Object> args){
		return (A) (new MappingProcess()).process(source, args);
	}
	/**
	 * @param	source The class to get the mapping
	 * @return	The mapping class
	 **/
	final public static Class<?> map(Class<?> source){
		MappingClass mappingClass = source.getAnnotation(MappingClass.class);
		if (mappingClass==null)
			return null;
		try {
			return mappingClass.type().equals(Object.class)? Class.forName(mappingClass.name()): mappingClass.type();
		} catch (ClassNotFoundException e) {
			System.err.println("No mapping class was found for "+mappingClass.name());
			return null;
		}		
	}
}
