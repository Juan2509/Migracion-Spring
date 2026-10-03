package com.migracion.rangel.application.gender.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
public class DuplicateGenderApplicationException extends ApplicationException {
    public DuplicateGenderApplicationException() {
        super("Ya existe un Gender con el mismo description.");
    }
}
