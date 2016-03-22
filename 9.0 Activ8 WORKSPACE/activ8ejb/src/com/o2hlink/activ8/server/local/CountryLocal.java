package com.o2hlink.activ8.server.local;

import java.util.List;

import javax.ejb.Local;

import com.o2hlink.activ8.client.entity.Country;
import com.o2hlink.activ8.client.entity.Province;

/**
 * Local operations on a country
 * @author Miguel Angel Hernandez
 **/
@Local
public interface CountryLocal {
	/**
	 * 
	 **/
	public List<Country> getCountries();
	/**
	 * 
	 **/
	public List<Province> getProvinces(Country country);
}
