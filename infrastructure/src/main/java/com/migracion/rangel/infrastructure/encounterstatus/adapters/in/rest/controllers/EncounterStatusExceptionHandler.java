package com.migracion.rangel.infrastructure.encounterstatus.adapters.in.rest.controllers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.encounterstatus.exception.EncounterStatusNotFoundApplicationException;
import com.migracion.rangel.application.encounterstatus.exception.DuplicateEncounterStatusApplicationException;
@RestControllerAdvice(assignableTypes = EncounterStatusController.class)
public class EncounterStatusExceptionHandler {
    @ExceptionHandler(EncounterStatusNotFoundApplicationException.class)
    public ProblemDetail notFound(EncounterStatusNotFoundApplicationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(DuplicateEncounterStatusApplicationException.class)
    public ProblemDetail duplicate(DuplicateEncounterStatusApplicationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}

