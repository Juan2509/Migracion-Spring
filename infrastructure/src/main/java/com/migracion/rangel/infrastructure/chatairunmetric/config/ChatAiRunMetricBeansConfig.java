package com.migracion.rangel.infrastructure.chatairunmetric.config;
import java.math.BigDecimal;
import com.migracion.rangel.domain.chatairun.port.repository.ChatAiRunRepository;
import com.migracion.rangel.application.chatairun.exception.ChatAiRunNotFoundApplicationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.chatairunmetric.port.repository.ChatAiRunMetricRepository;
import com.migracion.rangel.infrastructure.chatairunmetric.adapters.out.persistence.repositories.ChatAiRunMetricJpaRepository;
import com.migracion.rangel.infrastructure.chatairunmetric.adapters.out.persistence.repositories.ChatAiRunMetricRepositoryAdapter;
import com.migracion.rangel.infrastructure.chatairunmetric.adapters.out.persistence.mappers.ChatAiRunMetricPersistenceMapper;
import com.migracion.rangel.application.chatairunmetric.usecase.RegisterChatAiRunMetricUseCase;
import com.migracion.rangel.application.chatairunmetric.usecase.GetChatAiRunMetricByIdUseCase;
import com.migracion.rangel.application.chatairunmetric.usecase.ListChatAiRunMetricUseCase;
import com.migracion.rangel.application.chatairunmetric.usecase.UpdateChatAiRunMetricUseCase;
import com.migracion.rangel.application.chatairunmetric.usecase.DeleteChatAiRunMetricUseCase;
@Configuration
public class ChatAiRunMetricBeansConfig {
    @Bean
    public ChatAiRunMetricPersistenceMapper chatAiRunMetricPersistenceMapper() { return new ChatAiRunMetricPersistenceMapper(); }
    @Bean
    public ChatAiRunMetricRepository chatAiRunMetricRepository(ChatAiRunMetricJpaRepository repository, ChatAiRunMetricPersistenceMapper mapper) {
        return new ChatAiRunMetricRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterChatAiRunMetricUseCase registerChatAiRunMetricUseCase(ChatAiRunMetricRepository repository, ChatAiRunRepository runs) {
        return new RegisterChatAiRunMetricUseCase(repository, runs);
    }
    @Bean
    public GetChatAiRunMetricByIdUseCase getChatAiRunMetricByIdUseCase(ChatAiRunMetricRepository repository) {
        return new GetChatAiRunMetricByIdUseCase(repository);
    }
    @Bean
    public ListChatAiRunMetricUseCase listChatAiRunMetricUseCase(ChatAiRunMetricRepository repository) {
        return new ListChatAiRunMetricUseCase(repository);
    }
    @Bean
    public UpdateChatAiRunMetricUseCase updateChatAiRunMetricUseCase(ChatAiRunMetricRepository repository, ChatAiRunRepository runs) {
        return new UpdateChatAiRunMetricUseCase(repository, runs);
    }
    @Bean
    public DeleteChatAiRunMetricUseCase deleteChatAiRunMetricUseCase(ChatAiRunMetricRepository repository) {
        return new DeleteChatAiRunMetricUseCase(repository);
    }
}

