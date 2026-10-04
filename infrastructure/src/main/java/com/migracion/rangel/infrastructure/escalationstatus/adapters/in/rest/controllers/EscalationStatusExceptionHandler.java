package com.migracion.rangel.infrastructure.escalationstatus.adapters.in.rest.controllers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.escalationstatus.exception.EscalationStatusNotFoundApplicationException;
@RestControllerAdvice(assignableTypes = EscalationStatusController.class)
public class EscalationStatusExceptionHandler {
    @ExceptionHandler(EscalationStatusNotFoundApplicationException.class)
    public ProblemDetail notFound(EscalationStatusNotFoundApplicationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}
