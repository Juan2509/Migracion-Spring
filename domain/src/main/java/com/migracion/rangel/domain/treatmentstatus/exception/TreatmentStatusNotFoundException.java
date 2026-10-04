package com.migracion.rangel.domain.treatmentstatus.exception;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
public class TreatmentStatusNotFoundException extends RuntimeException {
    public TreatmentStatusNotFoundException(TreatmentStatusId id) { super("TreatmentStatus no encontrado: " + id.value()); }
}


