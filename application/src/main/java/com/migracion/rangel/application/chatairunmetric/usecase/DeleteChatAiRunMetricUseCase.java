package com.migracion.rangel.application.chatairunmetric.usecase;
import java.math.BigDecimal;
import com.migracion.rangel.domain.chatairunmetric.port.repository.ChatAiRunMetricRepository;
import com.migracion.rangel.domain.chatairunmetric.model.valueobject.ChatAiRunMetricId;
import com.migracion.rangel.application.chatairunmetric.dto.ChatAiRunMetricResponse;
import com.migracion.rangel.application.chatairunmetric.exception.ChatAiRunMetricNotFoundApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.chatairunmetric.event.ChatAiRunMetricDeletedEvent;
public class DeleteChatAiRunMetricUseCase {
    private final ChatAiRunMetricRepository repository;
    public DeleteChatAiRunMetricUseCase(ChatAiRunMetricRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ChatAiRunMetricDeletedEvent execute(ChatAiRunMetricId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new ChatAiRunMetricNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new ChatAiRunMetricDeletedEvent(id, LocalDateTime.now());
    }
}

