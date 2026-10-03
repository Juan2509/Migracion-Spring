package com.migracion.rangel.application.common.exception;

/** Base para los errores específicos de los casos de uso. */
public abstract class ApplicationException extends RuntimeException {

    protected ApplicationException(String message) {
        super(message);
    }

    protected ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
