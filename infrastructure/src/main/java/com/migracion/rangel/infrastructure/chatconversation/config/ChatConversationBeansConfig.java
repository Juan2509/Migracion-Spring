package com.migracion.rangel.infrastructure.chatconversation.config;
import com.migracion.rangel.domain.conversationstatus.port.repository.ConversationStatusRepository;
import com.migracion.rangel.domain.priority.port.repository.PriorityRepository;
import com.migracion.rangel.application.conversationstatus.exception.ConversationStatusNotFoundApplicationException;
import com.migracion.rangel.application.priority.exception.PriorityNotFoundApplicationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.infrastructure.chatconversation.adapters.out.persistence.repositories.ChatConversationJpaRepository;
import com.migracion.rangel.infrastructure.chatconversation.adapters.out.persistence.repositories.ChatConversationRepositoryAdapter;
import com.migracion.rangel.infrastructure.chatconversation.adapters.out.persistence.mappers.ChatConversationPersistenceMapper;
import com.migracion.rangel.application.chatconversation.usecase.RegisterChatConversationUseCase;
import com.migracion.rangel.application.chatconversation.usecase.GetChatConversationByIdUseCase;
import com.migracion.rangel.application.chatconversation.usecase.ListChatConversationUseCase;
import com.migracion.rangel.application.chatconversation.usecase.UpdateChatConversationUseCase;
import com.migracion.rangel.application.chatconversation.usecase.DeleteChatConversationUseCase;
@Configuration
public class ChatConversationBeansConfig {
    @Bean
    public ChatConversationPersistenceMapper chatConversationPersistenceMapper() { return new ChatConversationPersistenceMapper(); }
    @Bean
    public ChatConversationRepository chatConversationRepository(ChatConversationJpaRepository repository, ChatConversationPersistenceMapper mapper) {
        return new ChatConversationRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterChatConversationUseCase registerChatConversationUseCase(ChatConversationRepository repository, ConversationStatusRepository statuses, PriorityRepository priorities) {
        return new RegisterChatConversationUseCase(repository, statuses, priorities);
    }
    @Bean
    public GetChatConversationByIdUseCase getChatConversationByIdUseCase(ChatConversationRepository repository) {
        return new GetChatConversationByIdUseCase(repository);
    }
    @Bean
    public ListChatConversationUseCase listChatConversationUseCase(ChatConversationRepository repository) {
        return new ListChatConversationUseCase(repository);
    }
    @Bean
    public UpdateChatConversationUseCase updateChatConversationUseCase(ChatConversationRepository repository, ConversationStatusRepository statuses, PriorityRepository priorities) {
        return new UpdateChatConversationUseCase(repository, statuses, priorities);
    }
    @Bean
    public DeleteChatConversationUseCase deleteChatConversationUseCase(ChatConversationRepository repository) {
        return new DeleteChatConversationUseCase(repository);
    }
}

