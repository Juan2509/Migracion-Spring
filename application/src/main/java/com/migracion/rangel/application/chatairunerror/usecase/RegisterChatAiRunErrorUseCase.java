package com.migracion.rangel.application.chatairunerror.usecase;
import com.migracion.rangel.domain.chatairun.port.repository.ChatAiRunRepository;
import com.migracion.rangel.application.chatairun.exception.ChatAiRunNotFoundApplicationException;
import com.migracion.rangel.domain.chatairunerror.port.repository.ChatAiRunErrorRepository;
import com.migracion.rangel.domain.chatairunerror.model.valueobject.ChatAiRunErrorId;
import com.migracion.rangel.application.chatairunerror.dto.ChatAiRunErrorResponse;
import com.migracion.rangel.application.chatairunerror.exception.ChatAiRunErrorNotFoundApplicationException;
import com.migracion.rangel.application.chatairunerror.command.RegisterChatAiRunErrorCommand;
import com.migracion.rangel.domain.chatairunerror.model.aggregate.ChatAiRunError;
public class RegisterChatAiRunErrorUseCase {
    private final ChatAiRunErrorRepository repository;
    private final ChatAiRunRepository runs;
    public RegisterChatAiRunErrorUseCase(ChatAiRunErrorRepository repository, ChatAiRunRepository runs) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.runs = java.util.Objects.requireNonNull(runs);
    }
    public ChatAiRunErrorResponse execute(RegisterChatAiRunErrorCommand command) {
        var aggregate = ChatAiRunError.register(command.aiRunId(), command.errorMessage(), command.errorCode(), command.providerErrorId());
        runs.findById(command.aiRunId()).orElseThrow(() -> new ChatAiRunNotFoundApplicationException(command.aiRunId()));
        return ChatAiRunErrorResponse.from(repository.save(aggregate));
    }
}

