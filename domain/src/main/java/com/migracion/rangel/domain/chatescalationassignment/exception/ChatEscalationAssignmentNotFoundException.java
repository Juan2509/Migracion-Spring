package com.migracion.rangel.domain.chatescalationassignment.exception;
import com.migracion.rangel.domain.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;
public class ChatEscalationAssignmentNotFoundException extends RuntimeException {
    public ChatEscalationAssignmentNotFoundException(ChatEscalationAssignmentId id) { super("ChatEscalationAssignment no encontrado: " + id.value()); }
}

