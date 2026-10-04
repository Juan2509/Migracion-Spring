package com.migracion.rangel.infrastructure.riskassessment.adapters.in.rest.controllers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.riskassessment.exception.RiskAssessmentNotFoundApplicationException;
import com.migracion.rangel.application.encounter.exception.EncounterNotFoundApplicationException;
import com.migracion.rangel.application.risklevel.exception.RiskLevelNotFoundApplicationException;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
@RestControllerAdvice(assignableTypes = RiskAssessmentController.class)
public class RiskAssessmentExceptionHandler {
    @ExceptionHandler({RiskAssessmentNotFoundApplicationException.class, EncounterNotFoundApplicationException.class, RiskLevelNotFoundApplicationException.class, ProfessionalNotFoundApplicationException.class})
    public ProblemDetail notFound(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}
