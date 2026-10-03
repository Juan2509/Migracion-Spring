package com.migracion.rangel.application.study.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.study.exception.StudyNotFoundException;
import com.migracion.rangel.domain.study.model.valueobject.StudyId;
public class StudyNotFoundApplicationException extends ApplicationException {
    public StudyNotFoundApplicationException(StudyId id) {
        super("Study no encontrado: " + id.value(), new StudyNotFoundException(id));
    }
}
