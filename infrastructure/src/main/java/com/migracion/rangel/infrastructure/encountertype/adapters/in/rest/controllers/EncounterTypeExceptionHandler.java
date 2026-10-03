package com.migracion.rangel.infrastructure.encountertype.adapters.in.rest.controllers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.encountertype.exception.EncounterTypeNotFoundApplicationException;
import com.migracion.rangel.application.encountertype.exception.DuplicateEncounterTypeApplicationException;
@RestControllerAdvice(assignableTypes = EncounterTypeController.class)
public class EncounterTypeExceptionHandler {
    @ExceptionHandler(EncounterTypeNotFoundApplicationException.class)
    public ProblemDetail notFound(EncounterTypeNotFoundApplicationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(DuplicateEncounterTypeApplicationException.class)
    public ProblemDetail duplicate(DuplicateEncounterTypeApplicationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}

