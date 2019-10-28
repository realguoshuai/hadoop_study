package com.guoshuai.mtdap.common.exception;

public class RepetException extends Exception {

    private static final long serialVersionUID = 1L;

    public RepetException() {
        super();
    }

    public RepetException(String message) {
        super(message);
    }

    public RepetException(String message, Throwable clause) {
        super(message, clause);
    }

    public RepetException(Throwable clause) {
        super(clause);
    }
}
