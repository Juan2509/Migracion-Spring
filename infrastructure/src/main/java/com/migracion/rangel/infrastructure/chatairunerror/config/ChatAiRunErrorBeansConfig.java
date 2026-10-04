package com.migracion.rangel.infrastructure.chatairunerror.config;
import com.migracion.rangel.domain.chatairun.port.repository.ChatAiRunRepository;
import com.migracion.rangel.application.chatairun.exception.ChatAiRunNotFoundApplicationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.chatairunerror.port.repository.ChatAiRunErrorRepository;
import com.migracion.rangel.infrastructure.chatairunerror.adapters.out.persistence.repositories.ChatAiRunErrorJpaRepository;
import com.migracion.rangel.infrastructure.chatairunerror.adapters.out.persistence.repositories.ChatAiRunErrorRepositoryAdapter;
import com.migracion.rangel.infrastructure.chatairunerror.adapters.out.persistence.mappers.ChatAiRunErrorPersistenceMapper;
import com.migracion.rangel.application.chatairunerror.usecase.RegisterChatAiRunErrorUseCase;
import com.migracion.rangel.application.chatairunerror.usecase.GetChatAiRunErrorByIdUseCase;
import com.migracion.rangel.application.chatairunerror.usecase.ListChatAiRunErrorUseCase;
import com.migracion.rangel.application.chatairunerror.usecase.UpdateChatAiRunErrorUseCase;
import com.migracion.rangel.application.chatairunerror.usecase.DeleteChatAiRunErrorUseCase;
@Configuration
public class ChatAiRunErrorBeansConfig {
    @Bean
    public ChatAiRunErrorPersistenceMapper chatAiRunErrorPersistenceMapper() { return new ChatAiRunErrorPersistenceMapper(); }
    @Bean
    public ChatAiRunErrorRepository chatAiRunErrorRepository(ChatAiRunErrorJpaRepository repository, ChatAiRunErrorPersistenceMapper mapper) {
        return new ChatAiRunErrorRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterChatAiRunErrorUseCase registerChatAiRunErrorUseCase(ChatAiRunErrorRepository repository, ChatAiRunRepository runs) {
        return new RegisterChatAiRunErrorUseCase(repository, runs);
    }
    @Bean
    public GetChatAiRunErrorByIdUseCase getChatAiRunErrorByIdUseCase(ChatAiRunErrorRepository repository) {
        return new GetChatAiRunErrorByIdUseCase(repository);
    }
    @Bean
    public ListChatAiRunErrorUseCase listChatAiRunErrorUseCase(ChatAiRunErrorRepository repository) {
        return new ListChatAiRunErrorUseCase(repository);
    }
    @Bean
    public UpdateChatAiRunErrorUseCase updateChatAiRunErrorUseCase(ChatAiRunErrorRepository repository, ChatAiRunRepository runs) {
        return new UpdateChatAiRunErrorUseCase(repository, runs);
    }
    @Bean
    public DeleteChatAiRunErrorUseCase deleteChatAiRunErrorUseCase(ChatAiRunErrorRepository repository) {
        return new DeleteChatAiRunErrorUseCase(repository);
    }
}

