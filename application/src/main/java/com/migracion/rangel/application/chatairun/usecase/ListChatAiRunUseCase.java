package com.migracion.rangel.application.chatairun.usecase;
import com.migracion.rangel.domain.chatairun.port.repository.ChatAiRunRepository;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
import com.migracion.rangel.application.chatairun.dto.ChatAiRunResponse;
import com.migracion.rangel.application.chatairun.exception.ChatAiRunNotFoundApplicationException;
import java.util.List;
public class ListChatAiRunUseCase {
    private final ChatAiRunRepository repository;
    public ListChatAiRunUseCase(ChatAiRunRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<ChatAiRunResponse> execute() { return repository.findAll().stream().map(ChatAiRunResponse::from).toList(); }
}

