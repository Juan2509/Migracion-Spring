package com.migracion.rangel.application.mentalstatusexam.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.mentalstatusexam.exception.MentalStatusExamNotFoundException;
import com.migracion.rangel.domain.mentalstatusexam.model.valueobject.MentalStatusExamId;
public class MentalStatusExamNotFoundApplicationException extends ApplicationException {
    public MentalStatusExamNotFoundApplicationException(MentalStatusExamId id) {
        super("MentalStatusExam no encontrado: " + id.value(), new MentalStatusExamNotFoundException(id));
    }
}

