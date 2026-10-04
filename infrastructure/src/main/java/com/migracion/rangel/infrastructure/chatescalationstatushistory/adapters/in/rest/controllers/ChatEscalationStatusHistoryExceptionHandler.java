package com.migracion.rangel.infrastructure.chatescalationstatushistory.adapters.in.rest.controllers;
import com.migracion.rangel.application.escalationstatus.exception.EscalationStatusNotFoundApplicationException;
import com.migracion.rangel.application.chatescalation.exception.ChatEscalationNotFoundApplicationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.chatescalationstatushistory.exception.ChatEscalationStatusHistoryNotFoundApplicationException;
@RestControllerAdvice(assignableTypes = ChatEscalationStatusHistoryController.class)
public class ChatEscalationStatusHistoryExceptionHandler {
    @ExceptionHandler({ChatEscalationStatusHistoryNotFoundApplicationException.class, ChatEscalationNotFoundApplicationException.class, EscalationStatusNotFoundApplicationException.class})
    public ProblemDetail notFound(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}

