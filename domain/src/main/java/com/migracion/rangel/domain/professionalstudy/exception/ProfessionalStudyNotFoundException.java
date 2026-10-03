package com.migracion.rangel.domain.professionalstudy.exception;
import com.migracion.rangel.domain.professionalstudy.model.valueobject.ProfessionalStudyId;
public class ProfessionalStudyNotFoundException extends RuntimeException {
    public ProfessionalStudyNotFoundException(ProfessionalStudyId id) { super("No existe el estudio profesional " + id.value()); }
}
