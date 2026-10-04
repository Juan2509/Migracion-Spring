package com.migracion.rangel.domain.chatescalation.port.repository;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.chatescalation.model.aggregate.ChatEscalation;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
public interface ChatEscalationRepository {
    ChatEscalation save(ChatEscalation aggregate);
    Optional<ChatEscalation> findById(ChatEscalationId id);
    List<ChatEscalation> findAll();
    void delete(ChatEscalation aggregate);
}

