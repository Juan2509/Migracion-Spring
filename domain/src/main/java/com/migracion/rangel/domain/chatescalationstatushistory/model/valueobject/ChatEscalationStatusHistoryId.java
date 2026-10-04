package com.migracion.rangel.domain.chatescalationstatushistory.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record ChatEscalationStatusHistoryId(UUID value) {
    public ChatEscalationStatusHistoryId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static ChatEscalationStatusHistoryId generate() { return new ChatEscalationStatusHistoryId(UUID.randomUUID()); }
}

