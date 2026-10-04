package com.migracion.rangel.domain.chatconversation.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.chatconversation.model.aggregate.ChatConversation;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
public interface ChatConversationRepository {
    ChatConversation save(ChatConversation aggregate);
    Optional<ChatConversation> findById(ChatConversationId id);
    List<ChatConversation> findAll();
    void delete(ChatConversation aggregate);
}

