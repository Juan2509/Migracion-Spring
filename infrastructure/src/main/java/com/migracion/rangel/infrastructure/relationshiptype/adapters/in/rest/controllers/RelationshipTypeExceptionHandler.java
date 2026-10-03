package com.migracion.rangel.infrastructure.relationshiptype.adapters.in.rest.controllers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.relationshiptype.exception.RelationshipTypeNotFoundApplicationException;
import com.migracion.rangel.application.relationshiptype.exception.DuplicateRelationshipTypeApplicationException;
@RestControllerAdvice(assignableTypes = RelationshipTypeController.class)
public class RelationshipTypeExceptionHandler {
    @ExceptionHandler(RelationshipTypeNotFoundApplicationException.class)
    public ProblemDetail notFound(RelationshipTypeNotFoundApplicationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(DuplicateRelationshipTypeApplicationException.class)
    public ProblemDetail duplicate(DuplicateRelationshipTypeApplicationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}
