package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.HasProjects;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.response.ProjectListResponse;

/**
 * List the projects a provider has
 * @author Miguel Angel Hernandez
 **/
public class ProjectListGetAction implements Action<ProjectListResponse>, Cacheable {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 3097402105232433769L;
	/**
	 * 
	 **/
	private HasProjects provider;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public ProjectListGetAction(){
		
	}
	/**
	 * 
	 **/
	public ProjectListGetAction(HasProjects provider){
		setProvider(provider);
	}
//METHODS
	/**
	 * @param provider the provider to set
	 */
	public void setProvider(HasProjects provider) {
		this.provider = provider;
	}
	/**
	 * @return the provider
	 */
	@XmlElements({
		@XmlElement(name="clinician", type=Clinician.class),
		@XmlElement(name="researcher", type=Researcher.class),
	})
	public HasProjects getProvider() {
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
		if (obj instanceof ProjectListGetAction){
			ProjectListGetAction id = (ProjectListGetAction)obj;
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
