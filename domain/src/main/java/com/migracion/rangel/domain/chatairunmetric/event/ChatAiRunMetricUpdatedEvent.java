package com.migracion.rangel.domain.chatairunmetric.event;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.chatairunmetric.model.valueobject.ChatAiRunMetricId;
public record ChatAiRunMetricUpdatedEvent(ChatAiRunMetricId id, LocalDateTime occurredOn) implements DomainEvent {
    public ChatAiRunMetricUpdatedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

