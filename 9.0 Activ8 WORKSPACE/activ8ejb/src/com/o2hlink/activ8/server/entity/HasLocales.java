package com.o2hlink.activ8.server.entity;

import java.util.Map;

/**
 * If an object has multiple locales, i.e. is internationalized
 * @author Miguel Angel Hernandez
 **/
public interface HasLocales {
	/**
	 * Get the locales
	 **/
	public Map<String, HasLocale> getLocales();
}
