package com.enjoyor.mtdap.common.exception;

import org.springframework.http.HttpStatus;

public class SignificantRestException extends RuntimeException {

    private static final long serialVersionUID = 4055306559624907465L;
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public SignificantRestException() {
    }

    public SignificantRestException(String message) {
        super(message);
    }

    public SignificantRestException(HttpStatus stauts) {
        this.status = stauts;
    }

    public SignificantRestException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

}
