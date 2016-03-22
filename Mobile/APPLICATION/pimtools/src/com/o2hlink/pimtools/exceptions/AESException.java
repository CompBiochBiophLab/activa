package com.o2hlink.pimtools.exceptions;

/**
 * @author Adrian Rejas
 * 
 * This class deals with expections occured while managing AES keys.
 *
 */

public class AESException extends Exception {
    
    /**
     * This constructor initializes a exception by the message passed in.  
     */
    public AESException(String msg) {
        super(msg);
    }
}
