package com.migracion.rangel.domain.mentalstatusexam.exception;
import com.migracion.rangel.domain.mentalstatusexam.model.valueobject.MentalStatusExamId;
public class MentalStatusExamNotFoundException extends RuntimeException {
    public MentalStatusExamNotFoundException(MentalStatusExamId id) { super("MentalStatusExam no encontrado: " + id.value()); }
}

