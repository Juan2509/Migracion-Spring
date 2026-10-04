package com.migracion.rangel.application.chatparticipant.usecase;
import com.migracion.rangel.domain.chatparticipant.port.repository.ChatParticipantRepository;
import com.migracion.rangel.domain.chatparticipant.model.valueobject.ChatParticipantId;
import com.migracion.rangel.application.chatparticipant.dto.ChatParticipantResponse;
import com.migracion.rangel.application.chatparticipant.exception.ChatParticipantNotFoundApplicationException;
import java.util.List;
public class ListChatParticipantUseCase {
    private final ChatParticipantRepository repository;
    public ListChatParticipantUseCase(ChatParticipantRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<ChatParticipantResponse> execute() { return repository.findAll().stream().map(ChatParticipantResponse::from).toList(); }
}

