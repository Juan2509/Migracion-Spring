package com.migracion.rangel.domain.chatconversation.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
public record ChatConversationDeletedEvent(ChatConversationId id, LocalDateTime occurredOn) implements DomainEvent {
    public ChatConversationDeletedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

