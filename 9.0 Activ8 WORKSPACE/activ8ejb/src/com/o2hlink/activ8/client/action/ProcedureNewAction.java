/**
 * 
 */
package com.o2hlink.activ8.client.action;

import com.o2hlink.activ8.client.entity.Procedure;
import com.o2hlink.activ8.client.response.ProcedureResponse;

/**
 * Create a new measurement. New measurements include only new questionnaires.
 * @author Miguel Angel Hernandez
 **/
public class ProcedureNewAction implements Action<ProcedureResponse> {
//FIELDS
	/**
	 * 
	 */
	private static final long serialVersionUID = 9052412169346338403L;
	/**
	 * 
	 **/
	private Procedure procedure;
//CONSTRUCTOR
	/**
	 * Empty constructor to allow serialization
	 **/
	public ProcedureNewAction(){
		
	}
	/**
	 * 
	 **/
	public ProcedureNewAction(Procedure procedure){
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
