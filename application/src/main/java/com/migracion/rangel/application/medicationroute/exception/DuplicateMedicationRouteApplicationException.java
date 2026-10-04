package com.migracion.rangel.application.medicationroute.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
public class DuplicateMedicationRouteApplicationException extends ApplicationException {
    public DuplicateMedicationRouteApplicationException() {
        super("Ya existe un MedicationRoute con el mismo code.");
    }
}

