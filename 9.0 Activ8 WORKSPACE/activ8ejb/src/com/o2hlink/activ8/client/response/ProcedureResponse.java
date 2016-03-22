/**
 * 
 */
package com.o2hlink.activ8.client.response;

import com.o2hlink.activ8.client.entity.Procedure;

/**
 * Return a list of datasets
 * @author Miguel Angel Hernandez
 **/
public class ProcedureResponse implements Response {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 4908513348662132627L;
	/**
	 * 
	 **/
	private Procedure procedure;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public ProcedureResponse(){
		
	}
	/**
	 * 
	 **/
	public ProcedureResponse(Procedure procedure){
		setProcedure(procedure);
	}
//METHODS
	/**
	 * @param procedure the procedure to set
	 */
	public void setProcedure(Procedure procedure) {
		this.procedure = procedure;
	}
	/**
	 * @return the procedure
	 */
	public Procedure getProcedure() {
		return procedure;
	}
}
