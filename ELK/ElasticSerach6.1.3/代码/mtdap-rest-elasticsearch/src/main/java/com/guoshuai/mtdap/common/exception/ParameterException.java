package com.guoshuai.mtdap.common.exception;

public class ParameterException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6969860470806143197L;
	
	public ParameterException() {
    }

    public ParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterException(String message) {
        super(message);
    }

    public ParameterException(Throwable cause) {
        super(cause);
    }
}
