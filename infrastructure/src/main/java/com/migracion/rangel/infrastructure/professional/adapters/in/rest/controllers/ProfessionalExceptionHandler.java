package com.migracion.rangel.infrastructure.professional.adapters.in.rest.controllers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
import com.migracion.rangel.application.professional.exception.DuplicateProfessionalApplicationException;
import com.migracion.rangel.application.documenttype.exception.DocumentTypeNotFoundApplicationException;
import com.migracion.rangel.application.professionaltype.exception.ProfessionalTypeNotFoundApplicationException;
import com.migracion.rangel.application.citymunicipality.exception.CityMunicipalityNotFoundApplicationException;
@RestControllerAdvice(assignableTypes = ProfessionalController.class)
public class ProfessionalExceptionHandler {
    @ExceptionHandler({ProfessionalNotFoundApplicationException.class, DocumentTypeNotFoundApplicationException.class, ProfessionalTypeNotFoundApplicationException.class, CityMunicipalityNotFoundApplicationException.class})
    public ProblemDetail notFound(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(DuplicateProfessionalApplicationException.class)
    public ProblemDetail duplicate(DuplicateProfessionalApplicationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}
