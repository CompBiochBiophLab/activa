package com.o2hlink.activa.exceptions;

/**
 * @author Adrian Rejas
 * 
 * This class deals with expections occured while managing the connection to the main
 * server.
 *
 */

public class ConnectionException extends Exception {
    /**
     * <p>The error code</p>  
     */
    public int errCode;
    /**
     * This constructor initializes a exception by the message passed in.  
     */
    public ConnectionException(String msg, int errno) {
        super(msg);
        errCode = errno;
    }
}
