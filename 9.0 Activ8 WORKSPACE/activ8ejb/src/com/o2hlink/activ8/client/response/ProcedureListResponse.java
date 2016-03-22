/**
 * 
 */
package com.o2hlink.activ8.client.response;

import java.util.List;

import com.o2hlink.activ8.client.entity.Procedure;

/**
 * Return a list of datasets
 * @author Miguel Angel Hernandez
 **/
public class ProcedureListResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 4908513348662132627L;
	/**
	 * 
	 **/
	private List<Procedure> procedures;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public ProcedureListResponse(){
		
	}
	/**
	 * 
	 **/
	public ProcedureListResponse(List<Procedure> procedures){
		setProcedures(procedures);
	}
//METHODS
	/**
	 * @param procedure the procedure to set
	 */
	public void setProcedures(List<Procedure> procedures) {
		this.procedures = procedures;
	}
	/**
	 * @return the procedure
	 */
	public List<Procedure> getProcedures() {
		return procedures;
	}
}
