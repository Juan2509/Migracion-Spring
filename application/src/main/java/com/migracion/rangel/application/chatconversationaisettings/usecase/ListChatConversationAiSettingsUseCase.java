package com.migracion.rangel.application.chatconversationaisettings.usecase;
import com.migracion.rangel.domain.chatconversationaisettings.port.repository.ChatConversationAiSettingsRepository;
import com.migracion.rangel.domain.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
import com.migracion.rangel.application.chatconversationaisettings.dto.ChatConversationAiSettingsResponse;
import com.migracion.rangel.application.chatconversationaisettings.exception.ChatConversationAiSettingsNotFoundApplicationException;
import java.util.List;
public class ListChatConversationAiSettingsUseCase {
    private final ChatConversationAiSettingsRepository repository;
    public ListChatConversationAiSettingsUseCase(ChatConversationAiSettingsRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<ChatConversationAiSettingsResponse> execute() { return repository.findAll().stream().map(ChatConversationAiSettingsResponse::from).toList(); }
}

