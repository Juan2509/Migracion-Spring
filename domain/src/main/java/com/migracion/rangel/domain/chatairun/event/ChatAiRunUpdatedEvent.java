package com.migracion.rangel.domain.chatairun.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
public record ChatAiRunUpdatedEvent(ChatAiRunId id, LocalDateTime occurredOn) implements DomainEvent {
    public ChatAiRunUpdatedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

