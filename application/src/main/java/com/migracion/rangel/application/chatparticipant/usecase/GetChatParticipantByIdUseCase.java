package com.migracion.rangel.application.chatparticipant.usecase;
import com.migracion.rangel.domain.chatparticipant.port.repository.ChatParticipantRepository;
import com.migracion.rangel.domain.chatparticipant.model.valueobject.ChatParticipantId;
import com.migracion.rangel.application.chatparticipant.dto.ChatParticipantResponse;
import com.migracion.rangel.application.chatparticipant.exception.ChatParticipantNotFoundApplicationException;

public class GetChatParticipantByIdUseCase {
    private final ChatParticipantRepository repository;
    public GetChatParticipantByIdUseCase(ChatParticipantRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ChatParticipantResponse execute(ChatParticipantId id) { return ChatParticipantResponse.from(repository.findById(id).orElseThrow(() -> new ChatParticipantNotFoundApplicationException(id))); }
}

