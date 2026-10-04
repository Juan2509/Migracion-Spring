package com.migracion.rangel.application.chatconversationaisettings.usecase;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.domain.aimodel.port.repository.AiModelRepository;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import com.migracion.rangel.application.aimodel.exception.AiModelNotFoundApplicationException;
import com.migracion.rangel.domain.chatconversationaisettings.port.repository.ChatConversationAiSettingsRepository;
import com.migracion.rangel.domain.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
import com.migracion.rangel.application.chatconversationaisettings.dto.ChatConversationAiSettingsResponse;
import com.migracion.rangel.application.chatconversationaisettings.exception.ChatConversationAiSettingsNotFoundApplicationException;
import com.migracion.rangel.application.chatconversationaisettings.command.UpdateChatConversationAiSettingsCommand;
public class UpdateChatConversationAiSettingsUseCase {
    private final ChatConversationAiSettingsRepository repository;
    private final ChatConversationRepository conversations;
    private final AiModelRepository models;
    public UpdateChatConversationAiSettingsUseCase(ChatConversationAiSettingsRepository repository, ChatConversationRepository conversations, AiModelRepository models) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.conversations = java.util.Objects.requireNonNull(conversations);
        this.models = java.util.Objects.requireNonNull(models);
    }
    public ChatConversationAiSettingsResponse execute(UpdateChatConversationAiSettingsCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new ChatConversationAiSettingsNotFoundApplicationException(id));
        conversations.findById(command.conversationId()).orElseThrow(() -> new ChatConversationNotFoundApplicationException(command.conversationId()));
        models.findById(command.defaultModelId()).orElseThrow(() -> new AiModelNotFoundApplicationException(command.defaultModelId()));
        aggregate.update(command.conversationId(), command.aiEnabled(), command.defaultModelId());
        return ChatConversationAiSettingsResponse.from(repository.save(aggregate));
    }
}

