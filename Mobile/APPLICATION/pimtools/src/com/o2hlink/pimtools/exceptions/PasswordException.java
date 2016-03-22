package com.o2hlink.pimtools.exceptions;

/**
 * @author Adrian Rejas
 * 
 * This class deals with expections occured because the user provides a bad password.
 *
 */

public class PasswordException extends Exception {
    /**
     * <p>The error code</p>  
     */
    public int errCode;
    /**
     * This constructor initializes a exception by the message passed in.  
     */
    public PasswordException(String msg) {
        super(msg);
    }
}
