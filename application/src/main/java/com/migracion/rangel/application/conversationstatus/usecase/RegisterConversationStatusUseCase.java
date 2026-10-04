package com.migracion.rangel.application.conversationstatus.usecase;
import com.migracion.rangel.domain.conversationstatus.port.repository.ConversationStatusRepository;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
import com.migracion.rangel.application.conversationstatus.dto.ConversationStatusResponse;
import com.migracion.rangel.application.conversationstatus.exception.ConversationStatusNotFoundApplicationException;
import com.migracion.rangel.application.conversationstatus.command.RegisterConversationStatusCommand;
import com.migracion.rangel.domain.conversationstatus.model.aggregate.ConversationStatus;
public class RegisterConversationStatusUseCase {
    private final ConversationStatusRepository repository;
    public RegisterConversationStatusUseCase(ConversationStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ConversationStatusResponse execute(RegisterConversationStatusCommand command) {
        var aggregate = ConversationStatus.register(command.nameStatus());
        return ConversationStatusResponse.from(repository.save(aggregate));
    }
}
