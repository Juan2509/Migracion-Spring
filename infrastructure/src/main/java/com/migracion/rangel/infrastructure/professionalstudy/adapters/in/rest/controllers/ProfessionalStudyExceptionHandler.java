package com.migracion.rangel.infrastructure.professionalstudy.adapters.in.rest.controllers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.professionalstudy.exception.ProfessionalStudyNotFoundApplicationException;
import com.migracion.rangel.application.study.exception.StudyNotFoundApplicationException;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
import com.migracion.rangel.application.country.exception.CountryNotFoundApplicationException;
@RestControllerAdvice(assignableTypes = ProfessionalStudyController.class)
public class ProfessionalStudyExceptionHandler {
    @ExceptionHandler({ProfessionalStudyNotFoundApplicationException.class, StudyNotFoundApplicationException.class, ProfessionalNotFoundApplicationException.class, CountryNotFoundApplicationException.class})
    public ProblemDetail notFound(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}
