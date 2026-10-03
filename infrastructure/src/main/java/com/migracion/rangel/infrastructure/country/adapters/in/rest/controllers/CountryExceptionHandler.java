package com.migracion.rangel.infrastructure.country.adapters.in.rest.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.country.exception.CountryNotFoundApplicationException;

@RestControllerAdvice(assignableTypes = CountryController.class)
public class CountryExceptionHandler {
    @ExceptionHandler(CountryNotFoundApplicationException.class)
    public ProblemDetail notFound(CountryNotFoundApplicationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}
