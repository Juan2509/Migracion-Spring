package com.migracion.rangel.domain.chatairunmetric.port.repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.chatairunmetric.model.aggregate.ChatAiRunMetric;
import com.migracion.rangel.domain.chatairunmetric.model.valueobject.ChatAiRunMetricId;
public interface ChatAiRunMetricRepository {
    ChatAiRunMetric save(ChatAiRunMetric aggregate);
    Optional<ChatAiRunMetric> findById(ChatAiRunMetricId id);
    List<ChatAiRunMetric> findAll();
    void delete(ChatAiRunMetric aggregate);
}

