package com.migracion.rangel.domain.chatescalation.model.valueobject;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import java.util.Objects;
import java.util.UUID;
public record ChatEscalationId(UUID value) {
    public ChatEscalationId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static ChatEscalationId generate() { return new ChatEscalationId(UUID.randomUUID()); }
}

