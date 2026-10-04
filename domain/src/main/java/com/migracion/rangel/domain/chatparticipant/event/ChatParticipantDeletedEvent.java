package com.migracion.rangel.domain.chatparticipant.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.chatparticipant.model.valueobject.ChatParticipantId;
public record ChatParticipantDeletedEvent(ChatParticipantId id, LocalDateTime occurredOn) implements DomainEvent {
    public ChatParticipantDeletedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

