package com.migracion.rangel.domain.chatmessage.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.chatmessage.model.aggregate.ChatMessage;
import com.migracion.rangel.domain.chatmessage.model.valueobject.ChatMessageId;
public interface ChatMessageRepository {
    ChatMessage save(ChatMessage aggregate);
    Optional<ChatMessage> findById(ChatMessageId id);
    List<ChatMessage> findAll();
    void delete(ChatMessage aggregate);
}

