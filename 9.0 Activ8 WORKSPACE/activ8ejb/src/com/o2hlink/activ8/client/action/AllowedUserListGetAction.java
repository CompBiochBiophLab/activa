package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Dataset;
import com.o2hlink.activ8.client.entity.HasPrivacyPolicy;
import com.o2hlink.activ8.client.entity.Project;
import com.o2hlink.activ8.client.response.UserListResponse;

/**
 * Given an object implementing {@link HasPrivacyPolicy}, get the user who has access to a it
 * @author Miguel Angel Hernandez
 **/
public class AllowedUserListGetAction implements Action<UserListResponse>, Cacheable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -6202637129524518461L;
	/**
	 * 
	 **/
	private HasPrivacyPolicy provider;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public AllowedUserListGetAction(){
		
	}
	/**
	 * 
	 **/
	public AllowedUserListGetAction(HasPrivacyPolicy provider){
		setProvider(provider);
	}
//METHODS
	/**
	 * @param provider the provider to set
	 */
	public void setProvider(HasPrivacyPolicy provider) {
		this.provider = provider;
	}
	/**
	 * @return the provider
	 */
	@XmlElements({
		@XmlElement(name="dataset", type=Dataset.class),
		@XmlElement(name="project", type=Project.class)
	})
	public HasPrivacyPolicy getProvider() {
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
		if (obj instanceof AllowedUserListGetAction){
			AllowedUserListGetAction id = (AllowedUserListGetAction)obj;
			return getProvider().equals(id.getProvider());
		}
		return false;
	}
	/**
	 * 
	 **/
	@Override
	public int hashCode(){
		return getProvider().hashCode();
	}
}
