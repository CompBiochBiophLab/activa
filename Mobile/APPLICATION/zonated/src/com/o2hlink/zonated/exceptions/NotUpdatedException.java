package com.o2hlink.zonated.exceptions;

/**
 * @author Adrian Rejas
 * 
 * This class deals with expections occured while managing the connection to the main
 * server.
 *
 */

public class NotUpdatedException extends Exception {
    /**
     * <p>The error code</p>  
     */
    public int errCode;
    /**
     * This constructor initializes a exception by the message passed in.  
     */
    public NotUpdatedException() {
        super("Not updated error");
        errCode = 0;
    }
    /**
     * This constructor initializes a exception by the message passed in.  
     */
    public NotUpdatedException(String msg, int errno) {
        super(msg);
        errCode = errno;
    }
}
