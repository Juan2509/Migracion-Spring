package com.migracion.rangel.application.chatescalation.usecase;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import com.migracion.rangel.domain.chatescalation.port.repository.ChatEscalationRepository;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import com.migracion.rangel.application.chatescalation.dto.ChatEscalationResponse;
import com.migracion.rangel.application.chatescalation.exception.ChatEscalationNotFoundApplicationException;
import java.util.List;
public class ListChatEscalationUseCase {
    private final ChatEscalationRepository repository;
    public ListChatEscalationUseCase(ChatEscalationRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<ChatEscalationResponse> execute() { return repository.findAll().stream().map(ChatEscalationResponse::from).toList(); }
}

