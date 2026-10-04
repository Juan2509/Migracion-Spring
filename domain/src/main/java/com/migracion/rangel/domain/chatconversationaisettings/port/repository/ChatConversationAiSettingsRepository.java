package com.migracion.rangel.domain.chatconversationaisettings.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.chatconversationaisettings.model.aggregate.ChatConversationAiSettings;
import com.migracion.rangel.domain.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
public interface ChatConversationAiSettingsRepository {
    ChatConversationAiSettings save(ChatConversationAiSettings aggregate);
    Optional<ChatConversationAiSettings> findById(ChatConversationAiSettingsId id);
    List<ChatConversationAiSettings> findAll();
    void delete(ChatConversationAiSettings aggregate);
}

