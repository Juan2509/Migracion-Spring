package com.migracion.rangel.infrastructure.airunstatus.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.airunstatus.port.repository.AiRunStatusRepository;
import com.migracion.rangel.infrastructure.airunstatus.adapters.out.persistence.repositories.AiRunStatusJpaRepository;
import com.migracion.rangel.infrastructure.airunstatus.adapters.out.persistence.repositories.AiRunStatusRepositoryAdapter;
import com.migracion.rangel.infrastructure.airunstatus.adapters.out.persistence.mappers.AiRunStatusPersistenceMapper;
import com.migracion.rangel.application.airunstatus.usecase.RegisterAiRunStatusUseCase;
import com.migracion.rangel.application.airunstatus.usecase.GetAiRunStatusByIdUseCase;
import com.migracion.rangel.application.airunstatus.usecase.ListAiRunStatusUseCase;
import com.migracion.rangel.application.airunstatus.usecase.UpdateAiRunStatusUseCase;
import com.migracion.rangel.application.airunstatus.usecase.DeleteAiRunStatusUseCase;
@Configuration
public class AiRunStatusBeansConfig {
    @Bean
    public AiRunStatusPersistenceMapper airunstatusPersistenceMapper() { return new AiRunStatusPersistenceMapper(); }
    @Bean
    public AiRunStatusRepository airunstatusRepository(AiRunStatusJpaRepository repository, AiRunStatusPersistenceMapper mapper) {
        return new AiRunStatusRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterAiRunStatusUseCase registerAiRunStatusUseCase(AiRunStatusRepository repository) {
        return new RegisterAiRunStatusUseCase(repository);
    }
    @Bean
    public GetAiRunStatusByIdUseCase getAiRunStatusByIdUseCase(AiRunStatusRepository repository) {
        return new GetAiRunStatusByIdUseCase(repository);
    }
    @Bean
    public ListAiRunStatusUseCase listAiRunStatusUseCase(AiRunStatusRepository repository) {
        return new ListAiRunStatusUseCase(repository);
    }
    @Bean
    public UpdateAiRunStatusUseCase updateAiRunStatusUseCase(AiRunStatusRepository repository) {
        return new UpdateAiRunStatusUseCase(repository);
    }
    @Bean
    public DeleteAiRunStatusUseCase deleteAiRunStatusUseCase(AiRunStatusRepository repository) {
        return new DeleteAiRunStatusUseCase(repository);
    }
}
