package com.guoshuai.mtdap.common.exception;

public class ControllerException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 2392945910163283142L;

    public ControllerException() {
    }

    public ControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(Throwable cause) {
        super(cause);
    }

}
