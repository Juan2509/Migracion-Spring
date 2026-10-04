package com.migracion.rangel.application.conversationstatus.usecase;
import com.migracion.rangel.domain.conversationstatus.port.repository.ConversationStatusRepository;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
import com.migracion.rangel.application.conversationstatus.dto.ConversationStatusResponse;
import com.migracion.rangel.application.conversationstatus.exception.ConversationStatusNotFoundApplicationException;
import com.migracion.rangel.application.conversationstatus.command.UpdateConversationStatusCommand;
public class UpdateConversationStatusUseCase {
    private final ConversationStatusRepository repository;
    public UpdateConversationStatusUseCase(ConversationStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ConversationStatusResponse execute(UpdateConversationStatusCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new ConversationStatusNotFoundApplicationException(id));
        aggregate.update(command.nameStatus());
        return ConversationStatusResponse.from(repository.save(aggregate));
    }
}
