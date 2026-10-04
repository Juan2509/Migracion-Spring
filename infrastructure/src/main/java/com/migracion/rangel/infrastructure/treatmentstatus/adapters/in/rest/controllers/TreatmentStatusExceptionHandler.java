package com.migracion.rangel.infrastructure.treatmentstatus.adapters.in.rest.controllers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.treatmentstatus.exception.TreatmentStatusNotFoundApplicationException;
import com.migracion.rangel.application.treatmentstatus.exception.DuplicateTreatmentStatusApplicationException;
@RestControllerAdvice(assignableTypes = TreatmentStatusController.class)
public class TreatmentStatusExceptionHandler {
    @ExceptionHandler(TreatmentStatusNotFoundApplicationException.class)
    public ProblemDetail notFound(TreatmentStatusNotFoundApplicationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(DuplicateTreatmentStatusApplicationException.class)
    public ProblemDetail duplicate(DuplicateTreatmentStatusApplicationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}


