package com.migracion.rangel.application.chatparticipant.usecase;
import com.migracion.rangel.domain.chatparticipant.port.repository.ChatParticipantRepository;
import com.migracion.rangel.domain.chatparticipant.model.valueobject.ChatParticipantId;
import com.migracion.rangel.application.chatparticipant.dto.ChatParticipantResponse;
import com.migracion.rangel.application.chatparticipant.exception.ChatParticipantNotFoundApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.chatparticipant.event.ChatParticipantDeletedEvent;
public class DeleteChatParticipantUseCase {
    private final ChatParticipantRepository repository;
    public DeleteChatParticipantUseCase(ChatParticipantRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ChatParticipantDeletedEvent execute(ChatParticipantId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new ChatParticipantNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new ChatParticipantDeletedEvent(id, LocalDateTime.now());
    }
}

