package com.o2hlink.activ8.client;

import com.google.gwt.core.client.GWT;

/**
 * 
 **/
public interface Messages extends com.google.gwt.i18n.client.Messages {
	/**
	 * 
	 **/
	public final static Messages instance = GWT.create(Messages.class);
	/**
	 * 
	 **/
	@DefaultMessage("This service collects information from the questionnaires and information from the sensors. By creating an account in ActivA users can download software to your mobile phone and begin to send biometric information to your account, personal data section, which will be registered and protected.")
	public String personalized_care_services();
	/**
	 * 
	 **/
	@DefaultMessage("This service provides tools for systems biology and bioinformatics structure with information linked to state of the art public and private banks of databases.")
	public String research_services();
	/**
	 * 
	 **/
	@DefaultMessage("By creating an account in ActivA clinicians can monitor them more for their patients, benefiting from recent advances in bioinformatics research and create comparative research groups.")
	public String clinician_services();
}
