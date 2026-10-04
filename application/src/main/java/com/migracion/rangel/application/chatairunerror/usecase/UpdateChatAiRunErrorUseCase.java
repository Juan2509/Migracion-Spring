package com.migracion.rangel.application.chatairunerror.usecase;
import com.migracion.rangel.domain.chatairun.port.repository.ChatAiRunRepository;
import com.migracion.rangel.application.chatairun.exception.ChatAiRunNotFoundApplicationException;
import com.migracion.rangel.domain.chatairunerror.port.repository.ChatAiRunErrorRepository;
import com.migracion.rangel.domain.chatairunerror.model.valueobject.ChatAiRunErrorId;
import com.migracion.rangel.application.chatairunerror.dto.ChatAiRunErrorResponse;
import com.migracion.rangel.application.chatairunerror.exception.ChatAiRunErrorNotFoundApplicationException;
import com.migracion.rangel.application.chatairunerror.command.UpdateChatAiRunErrorCommand;
public class UpdateChatAiRunErrorUseCase {
    private final ChatAiRunErrorRepository repository;
    private final ChatAiRunRepository runs;
    public UpdateChatAiRunErrorUseCase(ChatAiRunErrorRepository repository, ChatAiRunRepository runs) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.runs = java.util.Objects.requireNonNull(runs);
    }
    public ChatAiRunErrorResponse execute(UpdateChatAiRunErrorCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new ChatAiRunErrorNotFoundApplicationException(id));
        runs.findById(command.aiRunId()).orElseThrow(() -> new ChatAiRunNotFoundApplicationException(command.aiRunId()));
        aggregate.update(command.aiRunId(), command.errorMessage(), command.errorCode(), command.providerErrorId());
        return ChatAiRunErrorResponse.from(repository.save(aggregate));
    }
}

