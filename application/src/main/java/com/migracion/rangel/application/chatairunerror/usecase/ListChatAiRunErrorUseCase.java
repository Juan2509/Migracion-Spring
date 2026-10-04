package com.migracion.rangel.application.chatairunerror.usecase;
import com.migracion.rangel.domain.chatairunerror.port.repository.ChatAiRunErrorRepository;
import com.migracion.rangel.domain.chatairunerror.model.valueobject.ChatAiRunErrorId;
import com.migracion.rangel.application.chatairunerror.dto.ChatAiRunErrorResponse;
import com.migracion.rangel.application.chatairunerror.exception.ChatAiRunErrorNotFoundApplicationException;
import java.util.List;
public class ListChatAiRunErrorUseCase {
    private final ChatAiRunErrorRepository repository;
    public ListChatAiRunErrorUseCase(ChatAiRunErrorRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<ChatAiRunErrorResponse> execute() { return repository.findAll().stream().map(ChatAiRunErrorResponse::from).toList(); }
}

