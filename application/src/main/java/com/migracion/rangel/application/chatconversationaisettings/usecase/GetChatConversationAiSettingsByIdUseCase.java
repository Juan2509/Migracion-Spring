package com.migracion.rangel.application.chatconversationaisettings.usecase;
import com.migracion.rangel.domain.chatconversationaisettings.port.repository.ChatConversationAiSettingsRepository;
import com.migracion.rangel.domain.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
import com.migracion.rangel.application.chatconversationaisettings.dto.ChatConversationAiSettingsResponse;
import com.migracion.rangel.application.chatconversationaisettings.exception.ChatConversationAiSettingsNotFoundApplicationException;

public class GetChatConversationAiSettingsByIdUseCase {
    private final ChatConversationAiSettingsRepository repository;
    public GetChatConversationAiSettingsByIdUseCase(ChatConversationAiSettingsRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ChatConversationAiSettingsResponse execute(ChatConversationAiSettingsId id) { return ChatConversationAiSettingsResponse.from(repository.findById(id).orElseThrow(() -> new ChatConversationAiSettingsNotFoundApplicationException(id))); }
}

