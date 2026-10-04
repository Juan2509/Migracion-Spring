package com.migracion.rangel.application.chatescalationstatushistory.usecase;
import com.migracion.rangel.domain.escalationstatus.port.repository.EscalationStatusRepository;
import com.migracion.rangel.application.escalationstatus.exception.EscalationStatusNotFoundApplicationException;
import com.migracion.rangel.domain.chatescalation.port.repository.ChatEscalationRepository;
import com.migracion.rangel.application.chatescalation.exception.ChatEscalationNotFoundApplicationException;
import com.migracion.rangel.domain.chatescalationstatushistory.port.repository.ChatEscalationStatusHistoryRepository;
import com.migracion.rangel.application.chatescalationstatushistory.dto.ChatEscalationStatusHistoryResponse;
import com.migracion.rangel.application.chatescalationstatushistory.exception.ChatEscalationStatusHistoryNotFoundApplicationException;
import com.migracion.rangel.application.chatescalationstatushistory.command.UpdateChatEscalationStatusHistoryCommand;
public class UpdateChatEscalationStatusHistoryUseCase {
    private final ChatEscalationStatusHistoryRepository repository;
    private final ChatEscalationRepository escalations;
    private final EscalationStatusRepository statuses;
    public UpdateChatEscalationStatusHistoryUseCase(ChatEscalationStatusHistoryRepository repository, ChatEscalationRepository escalations, EscalationStatusRepository statuses) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.escalations = java.util.Objects.requireNonNull(escalations);
        this.statuses = java.util.Objects.requireNonNull(statuses);
    }
    public ChatEscalationStatusHistoryResponse execute(UpdateChatEscalationStatusHistoryCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new ChatEscalationStatusHistoryNotFoundApplicationException(id));
        escalations.findById(command.escalationId()).orElseThrow(() -> new ChatEscalationNotFoundApplicationException(command.escalationId()));
        statuses.findById(command.escalationStatusId()).orElseThrow(() -> new EscalationStatusNotFoundApplicationException(command.escalationStatusId()));
        aggregate.update(command.escalationId(), command.escalationStatusId(), command.changedAt());
        return ChatEscalationStatusHistoryResponse.from(repository.save(aggregate));
    }
}

