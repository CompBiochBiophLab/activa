package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Dataset;
import com.o2hlink.activ8.client.entity.HasPrivacyPolicy;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Project;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * Given an object implementing {@link HasPrivacyPolicy}, add users to access it
 * @author Miguel Angel Hernandez
 **/
public class AllowedUserAddAction implements Action<VoidResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -6202637129524518461L;
	/**
	 * 
	 **/
	private HasPrivacyPolicy provider;
	/**
	 * 
	 **/
	private User user;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public AllowedUserAddAction(){
		
	}
	/**
	 * 
	 **/
	public AllowedUserAddAction(HasPrivacyPolicy provider, User user){
		setProvider(provider);
		setUser(user);
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
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the user
	 */
	@XmlElements({
		@XmlElement(name="patient", type=Patient.class),
		@XmlElement(name="clinician", type=Clinician.class),
		@XmlElement(name="researcher", type=Researcher.class),
	})
	public User getUser() {
		return user;
	}
}
