package com.o2hlink.healthgenius.exceptions;

/**
 * @author Adrian Rejas
 * 
 * This class deals with expections occured because the user does not exists.
 *
 */

public class LoginException extends Exception {
    /**
     * <p>The error code</p>  
     */
    public int errCode;
    /**
     * This constructor initializes a exception by the message passed in.  
     */
    public LoginException(String msg) {
        super(msg);
    }
}
