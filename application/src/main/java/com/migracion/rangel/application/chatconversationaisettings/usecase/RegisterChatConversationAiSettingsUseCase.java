package com.migracion.rangel.application.chatconversationaisettings.usecase;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.domain.aimodel.port.repository.AiModelRepository;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import com.migracion.rangel.application.aimodel.exception.AiModelNotFoundApplicationException;
import com.migracion.rangel.domain.chatconversationaisettings.port.repository.ChatConversationAiSettingsRepository;
import com.migracion.rangel.domain.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
import com.migracion.rangel.application.chatconversationaisettings.dto.ChatConversationAiSettingsResponse;
import com.migracion.rangel.application.chatconversationaisettings.exception.ChatConversationAiSettingsNotFoundApplicationException;
import com.migracion.rangel.application.chatconversationaisettings.command.RegisterChatConversationAiSettingsCommand;
import com.migracion.rangel.domain.chatconversationaisettings.model.aggregate.ChatConversationAiSettings;
public class RegisterChatConversationAiSettingsUseCase {
    private final ChatConversationAiSettingsRepository repository;
    private final ChatConversationRepository conversations;
    private final AiModelRepository models;
    public RegisterChatConversationAiSettingsUseCase(ChatConversationAiSettingsRepository repository, ChatConversationRepository conversations, AiModelRepository models) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.conversations = java.util.Objects.requireNonNull(conversations);
        this.models = java.util.Objects.requireNonNull(models);
    }
    public ChatConversationAiSettingsResponse execute(RegisterChatConversationAiSettingsCommand command) {
        var aggregate = ChatConversationAiSettings.register(command.conversationId(), command.aiEnabled(), command.defaultModelId());
        conversations.findById(command.conversationId()).orElseThrow(() -> new ChatConversationNotFoundApplicationException(command.conversationId()));
        models.findById(command.defaultModelId()).orElseThrow(() -> new AiModelNotFoundApplicationException(command.defaultModelId()));
        return ChatConversationAiSettingsResponse.from(repository.save(aggregate));
    }
}

