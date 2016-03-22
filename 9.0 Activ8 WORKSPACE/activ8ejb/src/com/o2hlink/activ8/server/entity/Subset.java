/**
 * 
 */
package com.o2hlink.activ8.server.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.o2hlink.activ8.common.entity.HasDescription;
import com.o2hlink.activ8.common.entity.HasName;
import com.o2hlink.activ8.common.entity.MappingClass;
import com.o2hlink.activ8.common.entity.MappingProperty;


/**
 * A subset is a bunch of samples to analyze
 * @author Miguel Angel Hernandez
 **/
@Entity
@Table(name="subset")
@MappingClass(type=com.o2hlink.activ8.client.entity.Subset.class)
public class Subset implements Serializable, HasName, HasDescription, HasSamples {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 4306393799959963744L;
	/**
	 * 
	 **/
	private long id;
	/**
	 * 
	 **/
	private String name;
	/**
	 * 
	 **/
	private String description;
	/**
	 * 
	 **/
	private Dataset dataset;
	/**
	 * 
	 **/
	private List<Sample> samples = new ArrayList<Sample>();
//CONSTRUCTOR
	/**
	 * 
	 **/
	public Subset(){
		
	}
//METHODS
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the id
	 */
	@Id
	@Column(name="id", nullable=false, updatable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@MappingProperty
	public long getId() {
		return id;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	@Column(name="name", length=127)
	@MappingProperty
	public String getName() {
		return name;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the description
	 */
	@Column(name="description", length=256)
	@MappingProperty
	public String getDescription() {
		return description;
	}
	/**
	 * @param dataset the dataset to set
	 */
	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}
	/**
	 * @return the dataset
	 */
	@ManyToOne(
			cascade={},
			targetEntity=Dataset.class)
	@JoinColumn(name="dataset")
	public Dataset getDataset() {
		return dataset;
	}
	/**
	 * @param samples the samples to set
	 */
	public void setSamples(List<Sample> samples) {
		this.samples = samples;
	}
	/**
	 * @return the samples
	 */
	@ManyToMany(
			cascade={},
			fetch=FetchType.LAZY,
			targetEntity=Sample.class)
	@JoinTable(
			name="subset_sample",
			joinColumns={
					@JoinColumn(name="subset")
			},
			inverseJoinColumns={
					@JoinColumn(name="patient"),
					@JoinColumn(name="measurement"),
					@JoinColumn(name="date"),
			})
	public List<Sample> getSamples() {
		return samples;
	}
}
