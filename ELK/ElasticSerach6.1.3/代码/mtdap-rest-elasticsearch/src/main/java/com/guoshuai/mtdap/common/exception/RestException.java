package com.guoshuai.mtdap.common.exception;

import org.springframework.http.HttpStatus;

public class RestException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -5956166253477840495L;

    public HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public RestException() {
    }

    public RestException(HttpStatus status) {
        this.status = status;
    }

    public RestException(String message) {
        super(message);
    }

    public RestException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public RestException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

}
