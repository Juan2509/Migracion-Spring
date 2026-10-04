package com.migracion.rangel.infrastructure.risklevel.adapters.in.rest.controllers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.risklevel.exception.RiskLevelNotFoundApplicationException;
import com.migracion.rangel.application.risklevel.exception.DuplicateRiskLevelApplicationException;
@RestControllerAdvice(assignableTypes = RiskLevelController.class)
public class RiskLevelExceptionHandler {
    @ExceptionHandler(RiskLevelNotFoundApplicationException.class)
    public ProblemDetail notFound(RiskLevelNotFoundApplicationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(DuplicateRiskLevelApplicationException.class)
    public ProblemDetail duplicate(DuplicateRiskLevelApplicationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}

