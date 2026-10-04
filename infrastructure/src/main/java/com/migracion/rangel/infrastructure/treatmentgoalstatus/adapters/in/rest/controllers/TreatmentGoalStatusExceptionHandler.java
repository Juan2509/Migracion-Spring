package com.migracion.rangel.infrastructure.treatmentgoalstatus.adapters.in.rest.controllers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.treatmentgoalstatus.exception.TreatmentGoalStatusNotFoundApplicationException;
import com.migracion.rangel.application.treatmentgoalstatus.exception.DuplicateTreatmentGoalStatusApplicationException;
@RestControllerAdvice(assignableTypes = TreatmentGoalStatusController.class)
public class TreatmentGoalStatusExceptionHandler {
    @ExceptionHandler(TreatmentGoalStatusNotFoundApplicationException.class)
    public ProblemDetail notFound(TreatmentGoalStatusNotFoundApplicationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(DuplicateTreatmentGoalStatusApplicationException.class)
    public ProblemDetail duplicate(DuplicateTreatmentGoalStatusApplicationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}



