package com.migracion.rangel.infrastructure.chatmessage.adapters.in.rest.controllers;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import com.migracion.rangel.domain.messagetype.port.repository.MessageTypeRepository;
import com.migracion.rangel.application.messagetype.exception.MessageTypeNotFoundApplicationException;
import com.migracion.rangel.domain.chatparticipant.port.repository.ChatParticipantRepository;
import com.migracion.rangel.application.chatparticipant.exception.ChatParticipantNotFoundApplicationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.migracion.rangel.application.chatmessage.exception.ChatMessageNotFoundApplicationException;
@RestControllerAdvice(assignableTypes = ChatMessageController.class)
public class ChatMessageExceptionHandler {
    @ExceptionHandler({ChatMessageNotFoundApplicationException.class, ChatConversationNotFoundApplicationException.class, MessageTypeNotFoundApplicationException.class, ChatParticipantNotFoundApplicationException.class})
    public ProblemDetail notFound(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail conflict(DataIntegrityViolationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "La operación entra en conflicto con las restricciones de la base de datos.");
    }
}

