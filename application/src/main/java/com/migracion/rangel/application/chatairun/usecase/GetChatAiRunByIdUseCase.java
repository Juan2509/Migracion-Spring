package com.migracion.rangel.application.chatairun.usecase;
import com.migracion.rangel.domain.chatairun.port.repository.ChatAiRunRepository;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
import com.migracion.rangel.application.chatairun.dto.ChatAiRunResponse;
import com.migracion.rangel.application.chatairun.exception.ChatAiRunNotFoundApplicationException;

public class GetChatAiRunByIdUseCase {
    private final ChatAiRunRepository repository;
    public GetChatAiRunByIdUseCase(ChatAiRunRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ChatAiRunResponse execute(ChatAiRunId id) { return ChatAiRunResponse.from(repository.findById(id).orElseThrow(() -> new ChatAiRunNotFoundApplicationException(id))); }
}

