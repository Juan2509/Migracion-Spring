package com.migracion.rangel.application.chatescalationassignment.exception;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.chatescalationassignment.exception.ChatEscalationAssignmentNotFoundException;
import com.migracion.rangel.domain.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;
public class ChatEscalationAssignmentNotFoundApplicationException extends ApplicationException {
    public ChatEscalationAssignmentNotFoundApplicationException(ChatEscalationAssignmentId id) {
        super("ChatEscalationAssignment no encontrado: " + id.value(), new ChatEscalationAssignmentNotFoundException(id));
    }
}

