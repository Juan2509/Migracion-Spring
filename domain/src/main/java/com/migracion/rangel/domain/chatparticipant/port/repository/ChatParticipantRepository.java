package com.migracion.rangel.domain.chatparticipant.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.chatparticipant.model.aggregate.ChatParticipant;
import com.migracion.rangel.domain.chatparticipant.model.valueobject.ChatParticipantId;
public interface ChatParticipantRepository {
    ChatParticipant save(ChatParticipant aggregate);
    Optional<ChatParticipant> findById(ChatParticipantId id);
    List<ChatParticipant> findAll();
    void delete(ChatParticipant aggregate);
}

