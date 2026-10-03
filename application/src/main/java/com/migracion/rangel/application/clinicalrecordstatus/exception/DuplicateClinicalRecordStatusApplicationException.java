package com.migracion.rangel.application.clinicalrecordstatus.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
public class DuplicateClinicalRecordStatusApplicationException extends ApplicationException {
    public DuplicateClinicalRecordStatusApplicationException(String field) { super("Ya existe un estado de historia clínica con ese " + field + "."); }
}
