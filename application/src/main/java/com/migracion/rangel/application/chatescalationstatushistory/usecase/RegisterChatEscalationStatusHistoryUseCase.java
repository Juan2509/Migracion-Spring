package com.migracion.rangel.application.chatescalationstatushistory.usecase;
import com.migracion.rangel.domain.escalationstatus.port.repository.EscalationStatusRepository;
import com.migracion.rangel.application.escalationstatus.exception.EscalationStatusNotFoundApplicationException;
import com.migracion.rangel.domain.chatescalation.port.repository.ChatEscalationRepository;
import com.migracion.rangel.application.chatescalation.exception.ChatEscalationNotFoundApplicationException;
import com.migracion.rangel.domain.chatescalationstatushistory.port.repository.ChatEscalationStatusHistoryRepository;
import com.migracion.rangel.application.chatescalationstatushistory.dto.ChatEscalationStatusHistoryResponse;
import com.migracion.rangel.application.chatescalationstatushistory.command.RegisterChatEscalationStatusHistoryCommand;
import com.migracion.rangel.domain.chatescalationstatushistory.model.aggregate.ChatEscalationStatusHistory;
public class RegisterChatEscalationStatusHistoryUseCase {
    private final ChatEscalationStatusHistoryRepository repository;
    private final ChatEscalationRepository escalations;
    private final EscalationStatusRepository statuses;
    public RegisterChatEscalationStatusHistoryUseCase(ChatEscalationStatusHistoryRepository repository, ChatEscalationRepository escalations, EscalationStatusRepository statuses) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.escalations = java.util.Objects.requireNonNull(escalations);
        this.statuses = java.util.Objects.requireNonNull(statuses);
    }
    public ChatEscalationStatusHistoryResponse execute(RegisterChatEscalationStatusHistoryCommand command) {
        var aggregate = ChatEscalationStatusHistory.register(command.escalationId(), command.escalationStatusId(), command.changedAt());
        escalations.findById(command.escalationId()).orElseThrow(() -> new ChatEscalationNotFoundApplicationException(command.escalationId()));
        statuses.findById(command.escalationStatusId()).orElseThrow(() -> new EscalationStatusNotFoundApplicationException(command.escalationStatusId()));
        return ChatEscalationStatusHistoryResponse.from(repository.save(aggregate));
    }
}

