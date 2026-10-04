package com.migracion.rangel.application.chatescalationstatushistory.usecase;
import com.migracion.rangel.domain.chatescalationstatushistory.port.repository.ChatEscalationStatusHistoryRepository;
import com.migracion.rangel.domain.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;
import com.migracion.rangel.application.chatescalationstatushistory.dto.ChatEscalationStatusHistoryResponse;
import com.migracion.rangel.application.chatescalationstatushistory.exception.ChatEscalationStatusHistoryNotFoundApplicationException;

public class GetChatEscalationStatusHistoryByIdUseCase {
    private final ChatEscalationStatusHistoryRepository repository;
    public GetChatEscalationStatusHistoryByIdUseCase(ChatEscalationStatusHistoryRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ChatEscalationStatusHistoryResponse execute(ChatEscalationStatusHistoryId id) { return ChatEscalationStatusHistoryResponse.from(repository.findById(id).orElseThrow(() -> new ChatEscalationStatusHistoryNotFoundApplicationException(id))); }
}

