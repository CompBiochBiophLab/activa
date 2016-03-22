package com.o2hlink.activ8.server.entity;

import java.util.Map;

import com.o2hlink.activ8.common.entity.HasDescription;
import com.o2hlink.activ8.common.entity.MappingFunction;

/**
 * @author Miguel Angel Hernandez
 **/
public class TranslateDescription implements MappingFunction<Map<String, HasLocale>, String> {
	/**
	 * 
	 **/
	public String map(Map<String, HasLocale> locales, Map<String, Object> args) {
		if (args.containsKey("locale")){
			String locale = (String) args.get("locale");
			if (locales.containsKey(locale))
				return ((HasDescription)locales.get(locale)).getDescription();
			if (locale.length()>3){
				locale = locale.substring(0, locale.length()-3);
				if (locales.containsKey(locale))
					return ((HasDescription)locales.get(locale)).getDescription();
			}
		}
		if (locales.containsKey(""))
			return ((HasDescription)locales.get("")).getDescription();
		return "";
	}
}
