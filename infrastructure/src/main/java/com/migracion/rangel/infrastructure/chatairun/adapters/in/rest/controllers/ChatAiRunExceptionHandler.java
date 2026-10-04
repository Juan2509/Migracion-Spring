package com.migracion.rangel.infrastructure.chatairun.adapters.in.rest.controllers;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import com.migracion.rangel.domain.chatmessage.port.repository.ChatMessageRepository;
import com.migracion.rangel.application.chatmessage.exception.ChatMessageNotFoundApplicationException;
import com.migracion.rangel.domain.aimodel.port.repository.AiModelRepository;
import com.migracion.rangel.application.aimodel.exception.AiModelNotFoundApplicationException;
import com.migracion.rangel.domain.airunstatus.port.repository.AiRunStatusRepository;
import com.migracion.rangel.application.airunstatus.exception.AiRunStatusNotFoundApplicationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.chatairun.exception.ChatAiRunNotFoundApplicationException;
@RestControllerAdvice(assignableTypes = ChatAiRunController.class)
public class ChatAiRunExceptionHandler {
    @ExceptionHandler({ChatAiRunNotFoundApplicationException.class, ChatConversationNotFoundApplicationException.class, ChatMessageNotFoundApplicationException.class, AiModelNotFoundApplicationException.class, AiRunStatusNotFoundApplicationException.class})
    public ProblemDetail notFound(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}

