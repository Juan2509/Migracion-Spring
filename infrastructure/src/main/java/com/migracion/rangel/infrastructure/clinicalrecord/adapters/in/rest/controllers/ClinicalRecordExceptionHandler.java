package com.migracion.rangel.infrastructure.clinicalrecord.adapters.in.rest.controllers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.clinicalrecord.exception.ClinicalRecordNotFoundApplicationException;

import com.migracion.rangel.application.patient.exception.PatientNotFoundApplicationException;
import com.migracion.rangel.application.clinicalrecordstatus.exception.ClinicalRecordStatusNotFoundApplicationException;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
@RestControllerAdvice(assignableTypes = ClinicalRecordController.class)
public class ClinicalRecordExceptionHandler {
    @ExceptionHandler({ClinicalRecordNotFoundApplicationException.class, PatientNotFoundApplicationException.class, ClinicalRecordStatusNotFoundApplicationException.class, ProfessionalNotFoundApplicationException.class})
    public ProblemDetail notFound(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}
