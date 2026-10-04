package com.migracion.rangel.domain.chatconversationaisettings.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
public record ChatConversationAiSettingsDeletedEvent(ChatConversationAiSettingsId id, LocalDateTime occurredOn) implements DomainEvent {
    public ChatConversationAiSettingsDeletedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

