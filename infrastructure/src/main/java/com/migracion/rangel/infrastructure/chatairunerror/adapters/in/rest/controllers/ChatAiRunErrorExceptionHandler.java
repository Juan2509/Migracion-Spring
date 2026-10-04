package com.migracion.rangel.infrastructure.chatairunerror.adapters.in.rest.controllers;
import com.migracion.rangel.domain.chatairun.port.repository.ChatAiRunRepository;
import com.migracion.rangel.application.chatairun.exception.ChatAiRunNotFoundApplicationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.chatairunerror.exception.ChatAiRunErrorNotFoundApplicationException;
@RestControllerAdvice(assignableTypes = ChatAiRunErrorController.class)
public class ChatAiRunErrorExceptionHandler {
    @ExceptionHandler({ChatAiRunErrorNotFoundApplicationException.class, ChatAiRunNotFoundApplicationException.class})
    public ProblemDetail notFound(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}

