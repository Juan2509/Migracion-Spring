package com.migracion.rangel.domain.chatairunmetric.model.valueobject;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
public record ChatAiRunMetricId(UUID value) {
    public ChatAiRunMetricId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static ChatAiRunMetricId generate() { return new ChatAiRunMetricId(UUID.randomUUID()); }
}

