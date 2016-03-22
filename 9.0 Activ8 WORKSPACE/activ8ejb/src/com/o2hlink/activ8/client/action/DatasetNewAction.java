/**
 * 
 */
package com.o2hlink.activ8.client.action;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Dataset;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.client.entity.User;
import com.o2hlink.activ8.client.response.DatasetResponse;

/**
 * Create a new dataset. A dataset is a collection of medical samples, patients and analytical subsets.
 * @author Miguel Angel Hernandez
 **/
public class DatasetNewAction implements Action<DatasetResponse> {
//FIELDS
	/**
	 * 
	 **/
	private static final long serialVersionUID = -2819274367291469525L;
	/**
	 * 
	 **/
	private User owner;
	/**
	 * 
	 **/
	private Dataset dataset;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public DatasetNewAction(){
		
	}
	/**
	 * 
	 **/
	public DatasetNewAction(User owner, Dataset dataset){
		setOwner(owner);
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
	 * @param owner the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}
	/**
	 * @return the owner
	 */
	@XmlElements({
		@XmlElement(name="patient", type=Patient.class),
		@XmlElement(name="clinician", type=Clinician.class),
		@XmlElement(name="researcher", type=Researcher.class)
	})
	public User getOwner() {
		return owner;
	}
}
