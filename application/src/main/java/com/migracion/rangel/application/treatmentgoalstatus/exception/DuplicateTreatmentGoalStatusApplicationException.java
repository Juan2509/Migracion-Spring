package com.migracion.rangel.application.treatmentgoalstatus.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
public class DuplicateTreatmentGoalStatusApplicationException extends ApplicationException {
    public DuplicateTreatmentGoalStatusApplicationException(String field) {
        super("Ya existe un TreatmentGoalStatus con el mismo " + field + ".");
    }
}



