package com.migracion.rangel.infrastructure.chatescalationstatushistory.config;
import com.migracion.rangel.domain.escalationstatus.port.repository.EscalationStatusRepository;
import com.migracion.rangel.domain.chatescalation.port.repository.ChatEscalationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.chatescalationstatushistory.port.repository.ChatEscalationStatusHistoryRepository;
import com.migracion.rangel.infrastructure.chatescalationstatushistory.adapters.out.persistence.repositories.ChatEscalationStatusHistoryJpaRepository;
import com.migracion.rangel.infrastructure.chatescalationstatushistory.adapters.out.persistence.repositories.ChatEscalationStatusHistoryRepositoryAdapter;
import com.migracion.rangel.infrastructure.chatescalationstatushistory.adapters.out.persistence.mappers.ChatEscalationStatusHistoryPersistenceMapper;
import com.migracion.rangel.application.chatescalationstatushistory.usecase.RegisterChatEscalationStatusHistoryUseCase;
import com.migracion.rangel.application.chatescalationstatushistory.usecase.GetChatEscalationStatusHistoryByIdUseCase;
import com.migracion.rangel.application.chatescalationstatushistory.usecase.ListChatEscalationStatusHistoryUseCase;
import com.migracion.rangel.application.chatescalationstatushistory.usecase.UpdateChatEscalationStatusHistoryUseCase;
import com.migracion.rangel.application.chatescalationstatushistory.usecase.DeleteChatEscalationStatusHistoryUseCase;
@Configuration
public class ChatEscalationStatusHistoryBeansConfig {
    @Bean
    public ChatEscalationStatusHistoryPersistenceMapper chatEscalationStatusHistoryPersistenceMapper() { return new ChatEscalationStatusHistoryPersistenceMapper(); }
    @Bean
    public ChatEscalationStatusHistoryRepository chatEscalationStatusHistoryRepository(ChatEscalationStatusHistoryJpaRepository repository, ChatEscalationStatusHistoryPersistenceMapper mapper) {
        return new ChatEscalationStatusHistoryRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterChatEscalationStatusHistoryUseCase registerChatEscalationStatusHistoryUseCase(ChatEscalationStatusHistoryRepository repository, ChatEscalationRepository escalations, EscalationStatusRepository statuses) {
        return new RegisterChatEscalationStatusHistoryUseCase(repository, escalations, statuses);
    }
    @Bean
    public GetChatEscalationStatusHistoryByIdUseCase getChatEscalationStatusHistoryByIdUseCase(ChatEscalationStatusHistoryRepository repository) {
        return new GetChatEscalationStatusHistoryByIdUseCase(repository);
    }
    @Bean
    public ListChatEscalationStatusHistoryUseCase listChatEscalationStatusHistoryUseCase(ChatEscalationStatusHistoryRepository repository) {
        return new ListChatEscalationStatusHistoryUseCase(repository);
    }
    @Bean
    public UpdateChatEscalationStatusHistoryUseCase updateChatEscalationStatusHistoryUseCase(ChatEscalationStatusHistoryRepository repository, ChatEscalationRepository escalations, EscalationStatusRepository statuses) {
        return new UpdateChatEscalationStatusHistoryUseCase(repository, escalations, statuses);
    }
    @Bean
    public DeleteChatEscalationStatusHistoryUseCase deleteChatEscalationStatusHistoryUseCase(ChatEscalationStatusHistoryRepository repository) {
        return new DeleteChatEscalationStatusHistoryUseCase(repository);
    }
}

