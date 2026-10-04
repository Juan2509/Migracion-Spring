package com.migracion.rangel.domain.chatescalationassignment.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record ChatEscalationAssignmentId(UUID value) {
    public ChatEscalationAssignmentId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static ChatEscalationAssignmentId generate() { return new ChatEscalationAssignmentId(UUID.randomUUID()); }
}

