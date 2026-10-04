package com.migracion.rangel.application.chatairunmetric.usecase;
import java.math.BigDecimal;
import com.migracion.rangel.domain.chatairunmetric.port.repository.ChatAiRunMetricRepository;
import com.migracion.rangel.domain.chatairunmetric.model.valueobject.ChatAiRunMetricId;
import com.migracion.rangel.application.chatairunmetric.dto.ChatAiRunMetricResponse;
import com.migracion.rangel.application.chatairunmetric.exception.ChatAiRunMetricNotFoundApplicationException;
import java.util.List;
public class ListChatAiRunMetricUseCase {
    private final ChatAiRunMetricRepository repository;
    public ListChatAiRunMetricUseCase(ChatAiRunMetricRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<ChatAiRunMetricResponse> execute() { return repository.findAll().stream().map(ChatAiRunMetricResponse::from).toList(); }
}

