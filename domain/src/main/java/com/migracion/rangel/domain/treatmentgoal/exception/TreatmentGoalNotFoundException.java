package com.migracion.rangel.domain.treatmentgoal.exception;
import com.migracion.rangel.domain.treatmentgoal.model.valueobject.TreatmentGoalId;
public class TreatmentGoalNotFoundException extends RuntimeException {
    public TreatmentGoalNotFoundException(TreatmentGoalId id) { super("TreatmentGoal no encontrado: " + id.value()); }
}

