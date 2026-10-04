package com.migracion.rangel.infrastructure.priority.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.priority.port.repository.PriorityRepository;
import com.migracion.rangel.infrastructure.priority.adapters.out.persistence.repositories.PriorityJpaRepository;
import com.migracion.rangel.infrastructure.priority.adapters.out.persistence.repositories.PriorityRepositoryAdapter;
import com.migracion.rangel.infrastructure.priority.adapters.out.persistence.mappers.PriorityPersistenceMapper;
import com.migracion.rangel.application.priority.usecase.RegisterPriorityUseCase;
import com.migracion.rangel.application.priority.usecase.GetPriorityByIdUseCase;
import com.migracion.rangel.application.priority.usecase.ListPriorityUseCase;
import com.migracion.rangel.application.priority.usecase.UpdatePriorityUseCase;
import com.migracion.rangel.application.priority.usecase.DeletePriorityUseCase;
@Configuration
public class PriorityBeansConfig {
    @Bean
    public PriorityPersistenceMapper priorityPersistenceMapper() { return new PriorityPersistenceMapper(); }
    @Bean
    public PriorityRepository priorityRepository(PriorityJpaRepository repository, PriorityPersistenceMapper mapper) {
        return new PriorityRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterPriorityUseCase registerPriorityUseCase(PriorityRepository repository) {
        return new RegisterPriorityUseCase(repository);
    }
    @Bean
    public GetPriorityByIdUseCase getPriorityByIdUseCase(PriorityRepository repository) {
        return new GetPriorityByIdUseCase(repository);
    }
    @Bean
    public ListPriorityUseCase listPriorityUseCase(PriorityRepository repository) {
        return new ListPriorityUseCase(repository);
    }
    @Bean
    public UpdatePriorityUseCase updatePriorityUseCase(PriorityRepository repository) {
        return new UpdatePriorityUseCase(repository);
    }
    @Bean
    public DeletePriorityUseCase deletePriorityUseCase(PriorityRepository repository) {
        return new DeletePriorityUseCase(repository);
    }
}
