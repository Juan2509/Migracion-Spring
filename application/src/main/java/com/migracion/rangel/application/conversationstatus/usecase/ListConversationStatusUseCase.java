package com.migracion.rangel.application.conversationstatus.usecase;
import com.migracion.rangel.domain.conversationstatus.port.repository.ConversationStatusRepository;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
import com.migracion.rangel.application.conversationstatus.dto.ConversationStatusResponse;
import com.migracion.rangel.application.conversationstatus.exception.ConversationStatusNotFoundApplicationException;
import java.util.List;
public class ListConversationStatusUseCase {
    private final ConversationStatusRepository repository;
    public ListConversationStatusUseCase(ConversationStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<ConversationStatusResponse> execute() { return repository.findAll().stream().map(ConversationStatusResponse::from).toList(); }
}
