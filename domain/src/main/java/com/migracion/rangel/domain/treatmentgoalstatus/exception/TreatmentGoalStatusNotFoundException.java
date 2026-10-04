package com.migracion.rangel.domain.treatmentgoalstatus.exception;
import com.migracion.rangel.domain.treatmentgoalstatus.model.valueobject.TreatmentGoalStatusId;
public class TreatmentGoalStatusNotFoundException extends RuntimeException {
    public TreatmentGoalStatusNotFoundException(TreatmentGoalStatusId id) { super("TreatmentGoalStatus no encontrado: " + id.value()); }
}



