package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.HasComments;
import com.o2hlink.activ8.client.entity.LogRecord;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.response.CommentListResponse;

/**
 * @author Miguel Angel Hernandez
 **/
public class CommentListGetAction implements Action<CommentListResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 5105007308130530350L;
	/**
	 * 
	 **/
	private HasComments provider;
//CONSTRUCTOR
	/**
	 * 
	 **/
	public CommentListGetAction(){
		
	}
	/**
	 * 
	 **/
	public CommentListGetAction(HasComments provider){
		setProvider(provider);
	}
//METHODS
	/**
	 * @param provider the provider to set
	 */
	public void setProvider(HasComments provider) {
		this.provider = provider;
	}
	/**
	 * @return the provider
	 */
	@XmlElements({
		@XmlElement(name="logRecord", type=LogRecord.class),
		@XmlElement(name="patient", type=Patient.class),
		@XmlElement(name="clinician", type=Clinician.class),
		@XmlElement(name="researcher", type=Researcher.class),
	})
	public HasComments getProvider() {
		return provider;
	}
}
