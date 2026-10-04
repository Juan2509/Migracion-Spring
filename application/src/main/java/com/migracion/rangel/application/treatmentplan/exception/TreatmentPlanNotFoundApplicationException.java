package com.migracion.rangel.application.treatmentplan.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.treatmentplan.exception.TreatmentPlanNotFoundException;
import com.migracion.rangel.domain.treatmentplan.model.valueobject.TreatmentPlanId;
public class TreatmentPlanNotFoundApplicationException extends ApplicationException {
    public TreatmentPlanNotFoundApplicationException(TreatmentPlanId id) {
        super("TreatmentPlan no encontrado: " + id.value(), new TreatmentPlanNotFoundException(id));
    }
}


