package com.migracion.rangel.domain.chatparticipant.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record ChatParticipantId(UUID value) {
    public ChatParticipantId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static ChatParticipantId generate() { return new ChatParticipantId(UUID.randomUUID()); }
}

