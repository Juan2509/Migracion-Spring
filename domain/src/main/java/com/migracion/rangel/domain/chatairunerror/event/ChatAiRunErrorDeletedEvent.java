package com.migracion.rangel.domain.chatairunerror.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.chatairunerror.model.valueobject.ChatAiRunErrorId;
public record ChatAiRunErrorDeletedEvent(ChatAiRunErrorId id, LocalDateTime occurredOn) implements DomainEvent {
    public ChatAiRunErrorDeletedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

