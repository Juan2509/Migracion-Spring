package com.migracion.rangel.application.chatconversationaisettings.usecase;
import com.migracion.rangel.domain.chatconversationaisettings.port.repository.ChatConversationAiSettingsRepository;
import com.migracion.rangel.domain.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
import com.migracion.rangel.application.chatconversationaisettings.dto.ChatConversationAiSettingsResponse;
import com.migracion.rangel.application.chatconversationaisettings.exception.ChatConversationAiSettingsNotFoundApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.chatconversationaisettings.event.ChatConversationAiSettingsDeletedEvent;
public class DeleteChatConversationAiSettingsUseCase {
    private final ChatConversationAiSettingsRepository repository;
    public DeleteChatConversationAiSettingsUseCase(ChatConversationAiSettingsRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ChatConversationAiSettingsDeletedEvent execute(ChatConversationAiSettingsId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new ChatConversationAiSettingsNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new ChatConversationAiSettingsDeletedEvent(id, LocalDateTime.now());
    }
}

