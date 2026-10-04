package com.migracion.rangel.domain.chatairunmetric.event;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.chatairunmetric.model.valueobject.ChatAiRunMetricId;
public record ChatAiRunMetricDeletedEvent(ChatAiRunMetricId id, LocalDateTime occurredOn) implements DomainEvent {
    public ChatAiRunMetricDeletedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

