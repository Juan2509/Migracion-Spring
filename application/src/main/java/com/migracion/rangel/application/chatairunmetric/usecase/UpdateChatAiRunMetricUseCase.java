package com.migracion.rangel.application.chatairunmetric.usecase;
import java.math.BigDecimal;
import com.migracion.rangel.domain.chatairun.port.repository.ChatAiRunRepository;
import com.migracion.rangel.application.chatairun.exception.ChatAiRunNotFoundApplicationException;
import com.migracion.rangel.domain.chatairunmetric.port.repository.ChatAiRunMetricRepository;
import com.migracion.rangel.domain.chatairunmetric.model.valueobject.ChatAiRunMetricId;
import com.migracion.rangel.application.chatairunmetric.dto.ChatAiRunMetricResponse;
import com.migracion.rangel.application.chatairunmetric.exception.ChatAiRunMetricNotFoundApplicationException;
import com.migracion.rangel.application.chatairunmetric.command.UpdateChatAiRunMetricCommand;
public class UpdateChatAiRunMetricUseCase {
    private final ChatAiRunMetricRepository repository;
    private final ChatAiRunRepository runs;
    public UpdateChatAiRunMetricUseCase(ChatAiRunMetricRepository repository, ChatAiRunRepository runs) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.runs = java.util.Objects.requireNonNull(runs);
    }
    public ChatAiRunMetricResponse execute(UpdateChatAiRunMetricCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new ChatAiRunMetricNotFoundApplicationException(id));
        runs.findById(command.aiRunId()).orElseThrow(() -> new ChatAiRunNotFoundApplicationException(command.aiRunId()));
        aggregate.update(command.aiRunId(), command.promptTokens(), command.completionTokens(), command.totalTokens(), command.cost());
        return ChatAiRunMetricResponse.from(repository.save(aggregate));
    }
}

