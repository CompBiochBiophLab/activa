/**
 * 
 */
package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Dataset;
import com.o2hlink.activ8.client.entity.HasDatasets;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.response.VoidResponse;

/**
 * Removes a dataset. A dataset is a collection of medical samples, patients and analytical subsets.
 * @author Miguel Angel Hernandez
 **/
public class DatasetRemoveAction implements Action<VoidResponse> {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = -2819274367291469525L;
	/**
	 * 
	 **/
	private HasDatasets provider;
	/**
	 * 
	 **/
	private Dataset dataset;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public DatasetRemoveAction(){
		
	}
	/**
	 * 
	 **/
	public DatasetRemoveAction(HasDatasets provider, Dataset dataset){
		setProvider(provider);
		setDataset(dataset);
	}
//METHODS
	/**
	 * @param dataset the dataset to set
	 */
	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}
	/**
	 * @return the dataset
	 */
	public Dataset getDataset() {
		return dataset;
	}
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
}
