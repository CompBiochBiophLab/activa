package com.o2hlink.activa.exceptions;

/**
 * @author Adrian Rejas
 * 
 * This class deals with expections occured while managing invalid values.
 *
 */

public class InvalidValueException extends Exception { 
	
    /**
     * This constructor initializes a exception by the message passed in.  
     */
	public InvalidValueException(java.lang.String s)
	{
		super(s);
	} 
	
    /**
     * This constructor initializes a exception by the proper exception.  
     */
	public InvalidValueException(java.lang.Exception e)
	{
		super(e.getMessage());
	}
}
