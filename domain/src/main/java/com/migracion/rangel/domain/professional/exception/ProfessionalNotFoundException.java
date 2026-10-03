package com.migracion.rangel.domain.professional.exception;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
public class ProfessionalNotFoundException extends RuntimeException {
    public ProfessionalNotFoundException(ProfessionalId id) { super("No existe el profesional " + id.value()); }
}
