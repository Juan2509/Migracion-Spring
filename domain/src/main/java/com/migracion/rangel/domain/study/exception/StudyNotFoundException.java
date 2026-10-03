package com.migracion.rangel.domain.study.exception;
import com.migracion.rangel.domain.study.model.valueobject.StudyId;
public class StudyNotFoundException extends RuntimeException {
    public StudyNotFoundException(StudyId id) { super("Study no encontrado: " + id.value()); }
}
