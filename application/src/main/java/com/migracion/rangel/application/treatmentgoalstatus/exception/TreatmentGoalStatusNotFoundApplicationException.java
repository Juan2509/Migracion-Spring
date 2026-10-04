package com.migracion.rangel.application.treatmentgoalstatus.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.treatmentgoalstatus.exception.TreatmentGoalStatusNotFoundException;
import com.migracion.rangel.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
public class TreatmentGoalStatusNotFoundApplicationException extends ApplicationException {
    public TreatmentGoalStatusNotFoundApplicationException(TreatmentGoalStatusId id) {
        super("TreatmentGoalStatus no encontrado: " + id.value(), new TreatmentGoalStatusNotFoundException(id));
    }
}



