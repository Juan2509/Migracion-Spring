package com.migracion.rangel.infrastructure.chatconversationaisettings.config;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.domain.aimodel.port.repository.AiModelRepository;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import com.migracion.rangel.application.aimodel.exception.AiModelNotFoundApplicationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.chatconversationaisettings.port.repository.ChatConversationAiSettingsRepository;
import com.migracion.rangel.infrastructure.chatconversationaisettings.adapters.out.persistence.repositories.ChatConversationAiSettingsJpaRepository;
import com.migracion.rangel.infrastructure.chatconversationaisettings.adapters.out.persistence.repositories.ChatConversationAiSettingsRepositoryAdapter;
import com.migracion.rangel.infrastructure.chatconversationaisettings.adapters.out.persistence.mappers.ChatConversationAiSettingsPersistenceMapper;
import com.migracion.rangel.application.chatconversationaisettings.usecase.RegisterChatConversationAiSettingsUseCase;
import com.migracion.rangel.application.chatconversationaisettings.usecase.GetChatConversationAiSettingsByIdUseCase;
import com.migracion.rangel.application.chatconversationaisettings.usecase.ListChatConversationAiSettingsUseCase;
import com.migracion.rangel.application.chatconversationaisettings.usecase.UpdateChatConversationAiSettingsUseCase;
import com.migracion.rangel.application.chatconversationaisettings.usecase.DeleteChatConversationAiSettingsUseCase;
@Configuration
public class ChatConversationAiSettingsBeansConfig {
    @Bean
    public ChatConversationAiSettingsPersistenceMapper chatConversationAiSettingsPersistenceMapper() { return new ChatConversationAiSettingsPersistenceMapper(); }
    @Bean
    public ChatConversationAiSettingsRepository chatConversationAiSettingsRepository(ChatConversationAiSettingsJpaRepository repository, ChatConversationAiSettingsPersistenceMapper mapper) {
        return new ChatConversationAiSettingsRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterChatConversationAiSettingsUseCase registerChatConversationAiSettingsUseCase(ChatConversationAiSettingsRepository repository, ChatConversationRepository conversations, AiModelRepository models) {
        return new RegisterChatConversationAiSettingsUseCase(repository, conversations, models);
    }
    @Bean
    public GetChatConversationAiSettingsByIdUseCase getChatConversationAiSettingsByIdUseCase(ChatConversationAiSettingsRepository repository) {
        return new GetChatConversationAiSettingsByIdUseCase(repository);
    }
    @Bean
    public ListChatConversationAiSettingsUseCase listChatConversationAiSettingsUseCase(ChatConversationAiSettingsRepository repository) {
        return new ListChatConversationAiSettingsUseCase(repository);
    }
    @Bean
    public UpdateChatConversationAiSettingsUseCase updateChatConversationAiSettingsUseCase(ChatConversationAiSettingsRepository repository, ChatConversationRepository conversations, AiModelRepository models) {
        return new UpdateChatConversationAiSettingsUseCase(repository, conversations, models);
    }
    @Bean
    public DeleteChatConversationAiSettingsUseCase deleteChatConversationAiSettingsUseCase(ChatConversationAiSettingsRepository repository) {
        return new DeleteChatConversationAiSettingsUseCase(repository);
    }
}

