package com.enjoyor.mtdap.common.exception;

public class NoServiceException extends RuntimeException{

	/**
	 * 
	 */
    public static final long serialVersionUID = 4414053930281679050L;
	
    public NoServiceException() {
    }

    public NoServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoServiceException(String message) {
        super(message);
    }

    public NoServiceException(Throwable cause) {
        super(cause);
    }
    
}
