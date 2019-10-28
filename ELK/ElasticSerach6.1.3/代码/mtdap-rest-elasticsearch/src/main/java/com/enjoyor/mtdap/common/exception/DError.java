package com.enjoyor.mtdap.common.exception;

import java.util.ArrayList;
import java.util.Collection;

public class DError {

    private String message;
    private int code;
    private Collection<IError> errors;

    public DError() {
    }

    public DError(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public DError(String message, int code, Collection<IError> errors) {
        this.message = message;
        this.code = code;
        this.errors = errors;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getErrors() {
        return this.errors;
    }

    public void setErrors(Collection<IError> errors) {
        this.errors = errors;
    }

    public static ErrorBuilder custom() {
        return new ErrorBuilder();
    }

    public static class ErrorBuilder {
        private String message;
        private int code;
        private Collection<IError> errors;

        public ErrorBuilder setMessage(String message) {
            this.message = message;
            return this;
        }

        public ErrorBuilder setCode(int code) {
            this.code = code;
            return this;
        }

        public ErrorBuilder setErrors(Collection<IError> errors) {
            this.errors = errors;
            return this;
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
        public ErrorBuilder addError(DError.IError iError) {
            if (this.errors == null) {
                this.errors = new ArrayList();
            }
            this.errors.add(iError);
            return this;
        }

        public DError build() {
            return new DError(this.message, this.code, this.errors);
        }
    }

    public static class IError {
        private String message;
        private int errorcode;

        public IError() {
        }

        public IError(String message, int errorcode) {
            this.message = message;
            this.errorcode = errorcode;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getErrorcode() {
            return this.errorcode;
        }

        public void setErrorcode(int errorcode) {
            this.errorcode = errorcode;
        }
    }
}
