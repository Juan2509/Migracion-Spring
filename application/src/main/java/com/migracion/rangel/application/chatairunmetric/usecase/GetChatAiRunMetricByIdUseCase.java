package com.migracion.rangel.application.chatairunmetric.usecase;
import java.math.BigDecimal;
import com.migracion.rangel.domain.chatairunmetric.port.repository.ChatAiRunMetricRepository;
import com.migracion.rangel.domain.chatairunmetric.model.valueobject.ChatAiRunMetricId;
import com.migracion.rangel.application.chatairunmetric.dto.ChatAiRunMetricResponse;
import com.migracion.rangel.application.chatairunmetric.exception.ChatAiRunMetricNotFoundApplicationException;

public class GetChatAiRunMetricByIdUseCase {
    private final ChatAiRunMetricRepository repository;
    public GetChatAiRunMetricByIdUseCase(ChatAiRunMetricRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ChatAiRunMetricResponse execute(ChatAiRunMetricId id) { return ChatAiRunMetricResponse.from(repository.findById(id).orElseThrow(() -> new ChatAiRunMetricNotFoundApplicationException(id))); }
}

