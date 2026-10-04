package com.migracion.rangel.application.treatmentgoal.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.treatmentgoal.exception.TreatmentGoalNotFoundException;
import com.migracion.rangel.domain.treatmentgoal.model.valueobject.TreatmentGoalId;
public class TreatmentGoalNotFoundApplicationException extends ApplicationException {
    public TreatmentGoalNotFoundApplicationException(TreatmentGoalId id) {
        super("TreatmentGoal no encontrado: " + id.value(), new TreatmentGoalNotFoundException(id));
    }
}

