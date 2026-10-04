package com.migracion.rangel.domain.chatmessage.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.chatmessage.model.valueobject.ChatMessageId;
public record ChatMessageRegisteredEvent(ChatMessageId id, LocalDateTime occurredOn) implements DomainEvent {
    public ChatMessageRegisteredEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

