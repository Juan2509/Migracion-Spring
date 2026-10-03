package com.migracion.rangel.domain.professionaltype.exception;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
public class ProfessionalTypeNotFoundException extends RuntimeException {
    public ProfessionalTypeNotFoundException(ProfessionalTypeId id) { super("ProfessionalType no encontrado: " + id.value()); }
}
