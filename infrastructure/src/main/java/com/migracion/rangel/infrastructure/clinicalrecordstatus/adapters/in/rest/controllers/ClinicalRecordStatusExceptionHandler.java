package com.migracion.rangel.infrastructure.clinicalrecordstatus.adapters.in.rest.controllers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.clinicalrecordstatus.exception.ClinicalRecordStatusNotFoundApplicationException;
import com.migracion.rangel.application.clinicalrecordstatus.exception.DuplicateClinicalRecordStatusApplicationException;

@RestControllerAdvice(assignableTypes = ClinicalRecordStatusController.class)
public class ClinicalRecordStatusExceptionHandler {
    @ExceptionHandler({ClinicalRecordStatusNotFoundApplicationException.class})
    public ProblemDetail notFound(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(DuplicateClinicalRecordStatusApplicationException.class)
    public ProblemDetail duplicate(DuplicateClinicalRecordStatusApplicationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}
