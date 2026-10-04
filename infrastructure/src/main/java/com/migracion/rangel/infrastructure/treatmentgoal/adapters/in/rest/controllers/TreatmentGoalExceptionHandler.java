package com.migracion.rangel.infrastructure.treatmentgoal.adapters.in.rest.controllers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.treatmentgoal.exception.TreatmentGoalNotFoundApplicationException;
import com.migracion.rangel.application.treatmentplan.exception.TreatmentPlanNotFoundApplicationException;
import com.migracion.rangel.application.treatmentgoalstatus.exception.TreatmentGoalStatusNotFoundApplicationException;
@RestControllerAdvice(assignableTypes = TreatmentGoalController.class)
public class TreatmentGoalExceptionHandler {
    @ExceptionHandler({TreatmentGoalNotFoundApplicationException.class, TreatmentPlanNotFoundApplicationException.class, TreatmentGoalStatusNotFoundApplicationException.class})
    public ProblemDetail notFound(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}

