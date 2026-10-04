package com.migracion.rangel.application.chatairunerror.usecase;
import com.migracion.rangel.domain.chatairunerror.port.repository.ChatAiRunErrorRepository;
import com.migracion.rangel.domain.chatairunerror.model.valueobject.ChatAiRunErrorId;
import com.migracion.rangel.application.chatairunerror.dto.ChatAiRunErrorResponse;
import com.migracion.rangel.application.chatairunerror.exception.ChatAiRunErrorNotFoundApplicationException;

public class GetChatAiRunErrorByIdUseCase {
    private final ChatAiRunErrorRepository repository;
    public GetChatAiRunErrorByIdUseCase(ChatAiRunErrorRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ChatAiRunErrorResponse execute(ChatAiRunErrorId id) { return ChatAiRunErrorResponse.from(repository.findById(id).orElseThrow(() -> new ChatAiRunErrorNotFoundApplicationException(id))); }
}

