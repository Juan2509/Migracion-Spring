package com.migracion.rangel.application.chatairunmetric.exception;
import java.math.BigDecimal;
import com.migracion.rangel.application.common.exception.ApplicationException;
import com.migracion.rangel.domain.chatairunmetric.exception.ChatAiRunMetricNotFoundException;
import com.migracion.rangel.domain.chatairunmetric.model.valueobject.ChatAiRunMetricId;
public class ChatAiRunMetricNotFoundApplicationException extends ApplicationException {
    public ChatAiRunMetricNotFoundApplicationException(ChatAiRunMetricId id) {
        super("ChatAiRunMetric no encontrado: " + id.value(), new ChatAiRunMetricNotFoundException(id));
    }
}

