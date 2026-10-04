package com.migracion.rangel.domain.chatairunmetric.exception;
import java.math.BigDecimal;
import com.migracion.rangel.domain.chatairunmetric.model.valueobject.ChatAiRunMetricId;
public class ChatAiRunMetricNotFoundException extends RuntimeException {
    public ChatAiRunMetricNotFoundException(ChatAiRunMetricId id) { super("ChatAiRunMetric no encontrado: " + id.value()); }
}

