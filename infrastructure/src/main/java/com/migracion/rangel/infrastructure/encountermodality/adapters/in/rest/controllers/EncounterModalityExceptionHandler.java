package com.migracion.rangel.infrastructure.encountermodality.adapters.in.rest.controllers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.encountermodality.exception.EncounterModalityNotFoundApplicationException;
import com.migracion.rangel.application.encountermodality.exception.DuplicateEncounterModalityApplicationException;
@RestControllerAdvice(assignableTypes = EncounterModalityController.class)
public class EncounterModalityExceptionHandler {
    @ExceptionHandler(EncounterModalityNotFoundApplicationException.class)
    public ProblemDetail notFound(EncounterModalityNotFoundApplicationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(DuplicateEncounterModalityApplicationException.class)
    public ProblemDetail duplicate(DuplicateEncounterModalityApplicationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}

