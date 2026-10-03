package com.migracion.rangel.infrastructure.emailcontact.adapters.in.rest.controllers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.emailcontact.exception.EmailContactNotFoundApplicationException;
import com.migracion.rangel.application.contact.exception.ContactNotFoundApplicationException;
import com.migracion.rangel.application.emailcontact.exception.DuplicateEmailContactApplicationException;
@RestControllerAdvice(assignableTypes = EmailContactController.class)
public class EmailContactExceptionHandler {
    @ExceptionHandler({EmailContactNotFoundApplicationException.class, ContactNotFoundApplicationException.class})
    public ProblemDetail notFound(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(DuplicateEmailContactApplicationException.class)
    public ProblemDetail duplicate(DuplicateEmailContactApplicationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}
