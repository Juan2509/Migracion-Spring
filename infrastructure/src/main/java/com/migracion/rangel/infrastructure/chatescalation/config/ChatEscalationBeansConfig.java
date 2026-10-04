package com.migracion.rangel.infrastructure.chatescalation.config;
import com.migracion.rangel.domain.escalationstatus.port.repository.EscalationStatusRepository;
import com.migracion.rangel.application.escalationstatus.exception.EscalationStatusNotFoundApplicationException;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.chatescalation.port.repository.ChatEscalationRepository;
import com.migracion.rangel.infrastructure.chatescalation.adapters.out.persistence.repositories.ChatEscalationJpaRepository;
import com.migracion.rangel.infrastructure.chatescalation.adapters.out.persistence.repositories.ChatEscalationRepositoryAdapter;
import com.migracion.rangel.infrastructure.chatescalation.adapters.out.persistence.mappers.ChatEscalationPersistenceMapper;
import com.migracion.rangel.application.chatescalation.usecase.RegisterChatEscalationUseCase;
import com.migracion.rangel.application.chatescalation.usecase.GetChatEscalationByIdUseCase;
import com.migracion.rangel.application.chatescalation.usecase.ListChatEscalationUseCase;
import com.migracion.rangel.application.chatescalation.usecase.UpdateChatEscalationUseCase;
import com.migracion.rangel.application.chatescalation.usecase.DeleteChatEscalationUseCase;
@Configuration
public class ChatEscalationBeansConfig {
    @Bean
    public ChatEscalationPersistenceMapper chatEscalationPersistenceMapper() { return new ChatEscalationPersistenceMapper(); }
    @Bean
    public ChatEscalationRepository chatEscalationRepository(ChatEscalationJpaRepository repository, ChatEscalationPersistenceMapper mapper) {
        return new ChatEscalationRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterChatEscalationUseCase registerChatEscalationUseCase(ChatEscalationRepository repository, ChatConversationRepository conversations, EscalationStatusRepository statuses) {
        return new RegisterChatEscalationUseCase(repository, conversations, statuses);
    }
    @Bean
    public GetChatEscalationByIdUseCase getChatEscalationByIdUseCase(ChatEscalationRepository repository) {
        return new GetChatEscalationByIdUseCase(repository);
    }
    @Bean
    public ListChatEscalationUseCase listChatEscalationUseCase(ChatEscalationRepository repository) {
        return new ListChatEscalationUseCase(repository);
    }
    @Bean
    public UpdateChatEscalationUseCase updateChatEscalationUseCase(ChatEscalationRepository repository, ChatConversationRepository conversations, EscalationStatusRepository statuses) {
        return new UpdateChatEscalationUseCase(repository, conversations, statuses);
    }
    @Bean
    public DeleteChatEscalationUseCase deleteChatEscalationUseCase(ChatEscalationRepository repository) {
        return new DeleteChatEscalationUseCase(repository);
    }
}

