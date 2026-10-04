package com.migracion.rangel.application.chatescalation.usecase;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import com.migracion.rangel.domain.chatescalation.port.repository.ChatEscalationRepository;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import com.migracion.rangel.application.chatescalation.dto.ChatEscalationResponse;
import com.migracion.rangel.application.chatescalation.exception.ChatEscalationNotFoundApplicationException;

public class GetChatEscalationByIdUseCase {
    private final ChatEscalationRepository repository;
    public GetChatEscalationByIdUseCase(ChatEscalationRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ChatEscalationResponse execute(ChatEscalationId id) { return ChatEscalationResponse.from(repository.findById(id).orElseThrow(() -> new ChatEscalationNotFoundApplicationException(id))); }
}

