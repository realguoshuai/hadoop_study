package com.enjoyor.mtdap.common.exception;

public class RepetException extends Exception {

    /**
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */
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
