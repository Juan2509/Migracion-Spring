package com.migracion.rangel.application.chatescalationstatushistory.usecase;
import com.migracion.rangel.domain.chatescalationstatushistory.port.repository.ChatEscalationStatusHistoryRepository;
import com.migracion.rangel.application.chatescalationstatushistory.dto.ChatEscalationStatusHistoryResponse;
import java.util.List;
public class ListChatEscalationStatusHistoryUseCase {
    private final ChatEscalationStatusHistoryRepository repository;
    public ListChatEscalationStatusHistoryUseCase(ChatEscalationStatusHistoryRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<ChatEscalationStatusHistoryResponse> execute() { return repository.findAll().stream().map(ChatEscalationStatusHistoryResponse::from).toList(); }
}

