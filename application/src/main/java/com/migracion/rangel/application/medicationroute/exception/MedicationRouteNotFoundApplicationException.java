package com.migracion.rangel.application.medicationroute.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.medicationroute.exception.MedicationRouteNotFoundException;
import com.migracion.rangel.domain.medicationroute.model.valueobject.MedicationRouteId;
public class MedicationRouteNotFoundApplicationException extends ApplicationException {
    public MedicationRouteNotFoundApplicationException(MedicationRouteId id) {
        super("MedicationRoute no encontrado: " + id.value(), new MedicationRouteNotFoundException(id));
    }
}

