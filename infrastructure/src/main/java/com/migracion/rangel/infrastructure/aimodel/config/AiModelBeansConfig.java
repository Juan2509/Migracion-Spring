package com.migracion.rangel.infrastructure.aimodel.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.aimodel.port.repository.AiModelRepository;
import com.migracion.rangel.infrastructure.aimodel.adapters.out.persistence.repositories.AiModelJpaRepository;
import com.migracion.rangel.infrastructure.aimodel.adapters.out.persistence.repositories.AiModelRepositoryAdapter;
import com.migracion.rangel.infrastructure.aimodel.adapters.out.persistence.mappers.AiModelPersistenceMapper;
import com.migracion.rangel.application.aimodel.usecase.RegisterAiModelUseCase;
import com.migracion.rangel.application.aimodel.usecase.GetAiModelByIdUseCase;
import com.migracion.rangel.application.aimodel.usecase.ListAiModelUseCase;
import com.migracion.rangel.application.aimodel.usecase.UpdateAiModelUseCase;
import com.migracion.rangel.application.aimodel.usecase.DeleteAiModelUseCase;
@Configuration
public class AiModelBeansConfig {
    @Bean
    public AiModelPersistenceMapper aiModelPersistenceMapper() { return new AiModelPersistenceMapper(); }
    @Bean
    public AiModelRepository aiModelRepository(AiModelJpaRepository repository, AiModelPersistenceMapper mapper) {
        return new AiModelRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterAiModelUseCase registerAiModelUseCase(AiModelRepository repository) {
        return new RegisterAiModelUseCase(repository);
    }
    @Bean
    public GetAiModelByIdUseCase getAiModelByIdUseCase(AiModelRepository repository) {
        return new GetAiModelByIdUseCase(repository);
    }
    @Bean
    public ListAiModelUseCase listAiModelUseCase(AiModelRepository repository) {
        return new ListAiModelUseCase(repository);
    }
    @Bean
    public UpdateAiModelUseCase updateAiModelUseCase(AiModelRepository repository) {
        return new UpdateAiModelUseCase(repository);
    }
    @Bean
    public DeleteAiModelUseCase deleteAiModelUseCase(AiModelRepository repository) {
        return new DeleteAiModelUseCase(repository);
    }
}

