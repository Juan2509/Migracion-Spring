package com.migracion.rangel.application.patient.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
public class DuplicatePatientApplicationException extends ApplicationException {
    public DuplicatePatientApplicationException() { super("Ya existe un paciente con ese correo."); }
}
