package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.HasDatasets;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.response.DatasetListResponse;

/**
 * Retrieve all the datasets a {@link HasDatasets} provider may have
 * @author Miguel Angel Hernandez
 **/
public class DatasetListGetAction implements Action<DatasetListResponse>, Cacheable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 5571803251467317623L;
	/**
	 * 
	 **/
	private HasDatasets provider;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public DatasetListGetAction(){
		
	}
	/**
	 * 
	 **/
	public DatasetListGetAction(HasDatasets provider){
		setProvider(provider);
	}
//METHODS
	/**
	 * @param provider the provider to set
	 */
	public void setProvider(HasDatasets provider) {
		this.provider = provider;
	}
	/**
	 * @return the provider
	 */
	@XmlElements({
		@XmlElement(name="clinician", type=Clinician.class),
		@XmlElement(name="researcher", type=Researcher.class)
	})
	public HasDatasets getProvider() {
		return provider;
	}
	/**
	 * 
	 **/
	public long getMaxAge() {
		return 60000;
	}
	/**
	 * 
	 **/
	@Override
	public boolean equals(Object obj){
		if (obj instanceof DatasetListGetAction){
			DatasetListGetAction id = (DatasetListGetAction)obj;
			return getProvider().equals(id.getProvider());
		}
		return false;
	}
	/**
	 * 
	 **/
	@Override
	public int hashCode() {
		return getProvider().hashCode();
	}
}
