package com.migracion.rangel.infrastructure.encounter.adapters.in.rest.controllers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.encounter.exception.EncounterNotFoundApplicationException;
import com.migracion.rangel.application.clinicalrecord.exception.ClinicalRecordNotFoundApplicationException;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
import com.migracion.rangel.application.encountertype.exception.EncounterTypeNotFoundApplicationException;
import com.migracion.rangel.application.encountermodality.exception.EncounterModalityNotFoundApplicationException;
import com.migracion.rangel.application.encounterstatus.exception.EncounterStatusNotFoundApplicationException;
@RestControllerAdvice(assignableTypes = EncounterController.class)
public class EncounterExceptionHandler {
    @ExceptionHandler({EncounterNotFoundApplicationException.class, ClinicalRecordNotFoundApplicationException.class, ProfessionalNotFoundApplicationException.class, EncounterTypeNotFoundApplicationException.class, EncounterModalityNotFoundApplicationException.class, EncounterStatusNotFoundApplicationException.class})
    public ProblemDetail notFound(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}
