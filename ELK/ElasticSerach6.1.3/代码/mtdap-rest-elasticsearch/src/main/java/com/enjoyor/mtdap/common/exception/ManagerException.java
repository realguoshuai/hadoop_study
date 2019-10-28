package com.enjoyor.mtdap.common.exception;

public class ManagerException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 6824176989556137343L;

    public ManagerException() {
    }

    public ManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ManagerException(String message) {
        super(message);
    }

    public ManagerException(Throwable cause) {
        super(cause);
    }

}
