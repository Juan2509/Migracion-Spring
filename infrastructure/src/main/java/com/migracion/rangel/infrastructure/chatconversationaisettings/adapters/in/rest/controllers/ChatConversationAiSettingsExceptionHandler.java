package com.migracion.rangel.infrastructure.chatconversationaisettings.adapters.in.rest.controllers;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.domain.aimodel.port.repository.AiModelRepository;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import com.migracion.rangel.application.aimodel.exception.AiModelNotFoundApplicationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.chatconversationaisettings.exception.ChatConversationAiSettingsNotFoundApplicationException;
@RestControllerAdvice(assignableTypes = ChatConversationAiSettingsController.class)
public class ChatConversationAiSettingsExceptionHandler {
    @ExceptionHandler({ChatConversationAiSettingsNotFoundApplicationException.class, ChatConversationNotFoundApplicationException.class, AiModelNotFoundApplicationException.class})
    public ProblemDetail notFound(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}

