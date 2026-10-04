package com.migracion.rangel.domain.treatmentplan.exception;
import com.migracion.rangel.domain.treatmentplan.model.valueobject.TreatmentPlanId;
public class TreatmentPlanNotFoundException extends RuntimeException {
    public TreatmentPlanNotFoundException(TreatmentPlanId id) { super("TreatmentPlan no encontrado: " + id.value()); }
}


