package com.migracion.rangel.infrastructure.encountermodality.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.encountermodality.port.repository.EncounterModalityRepository;
import com.migracion.rangel.infrastructure.encountermodality.adapters.out.persistence.repositories.EncounterModalityJpaRepository;
import com.migracion.rangel.infrastructure.encountermodality.adapters.out.persistence.repositories.EncounterModalityRepositoryAdapter;
import com.migracion.rangel.infrastructure.encountermodality.adapters.out.persistence.mappers.EncounterModalityPersistenceMapper;
import com.migracion.rangel.application.encountermodality.usecase.RegisterEncounterModalityUseCase;
import com.migracion.rangel.application.encountermodality.usecase.GetEncounterModalityByIdUseCase;
import com.migracion.rangel.application.encountermodality.usecase.ListEncounterModalityUseCase;
import com.migracion.rangel.application.encountermodality.usecase.UpdateEncounterModalityUseCase;
import com.migracion.rangel.application.encountermodality.usecase.DeleteEncounterModalityUseCase;
@Configuration
public class EncounterModalityBeansConfig {
    @Bean
    public EncounterModalityPersistenceMapper encounterModalityPersistenceMapper() { return new EncounterModalityPersistenceMapper(); }
    @Bean
    public EncounterModalityRepository encounterModalityRepository(EncounterModalityJpaRepository repository, EncounterModalityPersistenceMapper mapper) {
        return new EncounterModalityRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterEncounterModalityUseCase registerEncounterModalityUseCase(EncounterModalityRepository repository) {
        return new RegisterEncounterModalityUseCase(repository);
    }
    @Bean
    public GetEncounterModalityByIdUseCase getEncounterModalityByIdUseCase(EncounterModalityRepository repository) {
        return new GetEncounterModalityByIdUseCase(repository);
    }
    @Bean
    public ListEncounterModalityUseCase listEncounterModalityUseCase(EncounterModalityRepository repository) {
        return new ListEncounterModalityUseCase(repository);
    }
    @Bean
    public UpdateEncounterModalityUseCase updateEncounterModalityUseCase(EncounterModalityRepository repository) {
        return new UpdateEncounterModalityUseCase(repository);
    }
    @Bean
    public DeleteEncounterModalityUseCase deleteEncounterModalityUseCase(EncounterModalityRepository repository) {
        return new DeleteEncounterModalityUseCase(repository);
    }
}
