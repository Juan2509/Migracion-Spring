package com.migracion.rangel.application.treatmentstatus.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.treatmentstatus.exception.TreatmentStatusNotFoundException;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
public class TreatmentStatusNotFoundApplicationException extends ApplicationException {
    public TreatmentStatusNotFoundApplicationException(TreatmentStatusId id) {
        super("TreatmentStatus no encontrado: " + id.value(), new TreatmentStatusNotFoundException(id));
    }
}


