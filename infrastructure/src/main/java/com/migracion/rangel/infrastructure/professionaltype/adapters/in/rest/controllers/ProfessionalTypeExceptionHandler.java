package com.migracion.rangel.infrastructure.professionaltype.adapters.in.rest.controllers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.professionaltype.exception.ProfessionalTypeNotFoundApplicationException;
import com.migracion.rangel.application.professionaltype.exception.DuplicateProfessionalTypeApplicationException;
@RestControllerAdvice(assignableTypes = ProfessionalTypeController.class)
public class ProfessionalTypeExceptionHandler {
    @ExceptionHandler(ProfessionalTypeNotFoundApplicationException.class)
    public ProblemDetail notFound(ProfessionalTypeNotFoundApplicationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(DuplicateProfessionalTypeApplicationException.class)
    public ProblemDetail duplicate(DuplicateProfessionalTypeApplicationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}
