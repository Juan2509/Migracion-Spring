package com.migracion.rangel.infrastructure.chatairun.config;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import com.migracion.rangel.domain.chatmessage.port.repository.ChatMessageRepository;
import com.migracion.rangel.application.chatmessage.exception.ChatMessageNotFoundApplicationException;
import com.migracion.rangel.domain.aimodel.port.repository.AiModelRepository;
import com.migracion.rangel.application.aimodel.exception.AiModelNotFoundApplicationException;
import com.migracion.rangel.domain.airunstatus.port.repository.AiRunStatusRepository;
import com.migracion.rangel.application.airunstatus.exception.AiRunStatusNotFoundApplicationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.chatairun.port.repository.ChatAiRunRepository;
import com.migracion.rangel.infrastructure.chatairun.adapters.out.persistence.repositories.ChatAiRunJpaRepository;
import com.migracion.rangel.infrastructure.chatairun.adapters.out.persistence.repositories.ChatAiRunRepositoryAdapter;
import com.migracion.rangel.infrastructure.chatairun.adapters.out.persistence.mappers.ChatAiRunPersistenceMapper;
import com.migracion.rangel.application.chatairun.usecase.RegisterChatAiRunUseCase;
import com.migracion.rangel.application.chatairun.usecase.GetChatAiRunByIdUseCase;
import com.migracion.rangel.application.chatairun.usecase.ListChatAiRunUseCase;
import com.migracion.rangel.application.chatairun.usecase.UpdateChatAiRunUseCase;
import com.migracion.rangel.application.chatairun.usecase.DeleteChatAiRunUseCase;
@Configuration
public class ChatAiRunBeansConfig {
    @Bean
    public ChatAiRunPersistenceMapper chatAiRunPersistenceMapper() { return new ChatAiRunPersistenceMapper(); }
    @Bean
    public ChatAiRunRepository chatAiRunRepository(ChatAiRunJpaRepository repository, ChatAiRunPersistenceMapper mapper) {
        return new ChatAiRunRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterChatAiRunUseCase registerChatAiRunUseCase(ChatAiRunRepository repository, ChatConversationRepository conversations, ChatMessageRepository messages, AiModelRepository models, AiRunStatusRepository statuses) {
        return new RegisterChatAiRunUseCase(repository, conversations, messages, models, statuses);
    }
    @Bean
    public GetChatAiRunByIdUseCase getChatAiRunByIdUseCase(ChatAiRunRepository repository) {
        return new GetChatAiRunByIdUseCase(repository);
    }
    @Bean
    public ListChatAiRunUseCase listChatAiRunUseCase(ChatAiRunRepository repository) {
        return new ListChatAiRunUseCase(repository);
    }
    @Bean
    public UpdateChatAiRunUseCase updateChatAiRunUseCase(ChatAiRunRepository repository, ChatConversationRepository conversations, ChatMessageRepository messages, AiModelRepository models, AiRunStatusRepository statuses) {
        return new UpdateChatAiRunUseCase(repository, conversations, messages, models, statuses);
    }
    @Bean
    public DeleteChatAiRunUseCase deleteChatAiRunUseCase(ChatAiRunRepository repository) {
        return new DeleteChatAiRunUseCase(repository);
    }
}

