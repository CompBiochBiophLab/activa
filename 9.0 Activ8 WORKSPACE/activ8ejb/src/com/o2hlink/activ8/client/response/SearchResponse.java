package com.o2hlink.activ8.client.response;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.o2hlink.activ8.client.entity.Clinician;
import com.o2hlink.activ8.client.entity.Disease;
import com.o2hlink.activ8.client.entity.Event;
import com.o2hlink.activ8.client.entity.Gene;
import com.o2hlink.activ8.client.entity.Group;
import com.o2hlink.activ8.client.entity.Institution;
import com.o2hlink.activ8.client.entity.Paper;
import com.o2hlink.activ8.client.entity.Patient;
import com.o2hlink.activ8.client.entity.Protein;
import com.o2hlink.activ8.client.entity.Researcher;
import com.o2hlink.activ8.common.entity.HasSummary;

/**
 * Return a list of search hits
 * @author Miguel Angel Hernandez
 **/
public class SearchResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = -3432468695077228517L;
	/**
	 * 
	 **/
	private List<HasSummary> hits;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public SearchResponse(){
		
	}
	/**
	 * 
	 **/
	public SearchResponse(List<HasSummary> result){
		setHits(result);
	}
	/**
	 * @param hits the hits to set
	 */
	public void setHits(List<HasSummary> hits) {
		this.hits = hits;
	}
	/**
	 * @return the hits
	 */
	@XmlElements({
		@XmlElement(name="Disease", type=Disease.class),
		@XmlElement(name="Event", type=Event.class),
		@XmlElement(name="Gene", type=Gene.class),
		@XmlElement(name="Group", type=Group.class),
		@XmlElement(name="Institution", type=Institution.class),
		@XmlElement(name="Paper", type=Paper.class),
		@XmlElement(name="Protein", type=Protein.class),
		@XmlElement(name="Patient", type=Patient.class),
		@XmlElement(name="Clinician", type=Clinician.class),
		@XmlElement(name="Researcher", type=Researcher.class),
	})
	public List<HasSummary> getHits() {
		return hits;
	}
}
