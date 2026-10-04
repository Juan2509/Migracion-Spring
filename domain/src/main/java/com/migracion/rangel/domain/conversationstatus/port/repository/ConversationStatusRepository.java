package com.migracion.rangel.domain.conversationstatus.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.conversationstatus.model.aggregate.ConversationStatus;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
public interface ConversationStatusRepository {
    ConversationStatus save(ConversationStatus aggregate);
    Optional<ConversationStatus> findById(ConversationStatusId id);
    List<ConversationStatus> findAll();
    void delete(ConversationStatus aggregate);
}
