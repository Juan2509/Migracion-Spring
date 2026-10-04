package com.migracion.rangel.infrastructure.chatescalationassignment.adapters.in.rest.controllers;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
import com.migracion.rangel.application.chatescalation.exception.ChatEscalationNotFoundApplicationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.chatescalationassignment.exception.ChatEscalationAssignmentNotFoundApplicationException;
@RestControllerAdvice(assignableTypes = ChatEscalationAssignmentController.class)
public class ChatEscalationAssignmentExceptionHandler {
    @ExceptionHandler({ChatEscalationAssignmentNotFoundApplicationException.class, ChatEscalationNotFoundApplicationException.class, ProfessionalNotFoundApplicationException.class})
    public ProblemDetail notFound(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}

