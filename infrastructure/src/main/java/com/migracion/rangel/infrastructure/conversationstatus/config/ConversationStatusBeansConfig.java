package com.migracion.rangel.infrastructure.conversationstatus.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.conversationstatus.port.repository.ConversationStatusRepository;
import com.migracion.rangel.infrastructure.conversationstatus.adapters.out.persistence.repositories.ConversationStatusJpaRepository;
import com.migracion.rangel.infrastructure.conversationstatus.adapters.out.persistence.repositories.ConversationStatusRepositoryAdapter;
import com.migracion.rangel.infrastructure.conversationstatus.adapters.out.persistence.mappers.ConversationStatusPersistenceMapper;
import com.migracion.rangel.application.conversationstatus.usecase.RegisterConversationStatusUseCase;
import com.migracion.rangel.application.conversationstatus.usecase.GetConversationStatusByIdUseCase;
import com.migracion.rangel.application.conversationstatus.usecase.ListConversationStatusUseCase;
import com.migracion.rangel.application.conversationstatus.usecase.UpdateConversationStatusUseCase;
import com.migracion.rangel.application.conversationstatus.usecase.DeleteConversationStatusUseCase;
@Configuration
public class ConversationStatusBeansConfig {
    @Bean
    public ConversationStatusPersistenceMapper conversationstatusPersistenceMapper() { return new ConversationStatusPersistenceMapper(); }
    @Bean
    public ConversationStatusRepository conversationstatusRepository(ConversationStatusJpaRepository repository, ConversationStatusPersistenceMapper mapper) {
        return new ConversationStatusRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterConversationStatusUseCase registerConversationStatusUseCase(ConversationStatusRepository repository) {
        return new RegisterConversationStatusUseCase(repository);
    }
    @Bean
    public GetConversationStatusByIdUseCase getConversationStatusByIdUseCase(ConversationStatusRepository repository) {
        return new GetConversationStatusByIdUseCase(repository);
    }
    @Bean
    public ListConversationStatusUseCase listConversationStatusUseCase(ConversationStatusRepository repository) {
        return new ListConversationStatusUseCase(repository);
    }
    @Bean
    public UpdateConversationStatusUseCase updateConversationStatusUseCase(ConversationStatusRepository repository) {
        return new UpdateConversationStatusUseCase(repository);
    }
    @Bean
    public DeleteConversationStatusUseCase deleteConversationStatusUseCase(ConversationStatusRepository repository) {
        return new DeleteConversationStatusUseCase(repository);
    }
}
