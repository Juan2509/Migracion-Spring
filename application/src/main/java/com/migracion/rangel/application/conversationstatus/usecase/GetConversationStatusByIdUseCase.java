package com.migracion.rangel.application.conversationstatus.usecase;
import com.migracion.rangel.domain.conversationstatus.port.repository.ConversationStatusRepository;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
import com.migracion.rangel.application.conversationstatus.dto.ConversationStatusResponse;
import com.migracion.rangel.application.conversationstatus.exception.ConversationStatusNotFoundApplicationException;

public class GetConversationStatusByIdUseCase {
    private final ConversationStatusRepository repository;
    public GetConversationStatusByIdUseCase(ConversationStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ConversationStatusResponse execute(ConversationStatusId id) { return ConversationStatusResponse.from(repository.findById(id).orElseThrow(() -> new ConversationStatusNotFoundApplicationException(id))); }
}
