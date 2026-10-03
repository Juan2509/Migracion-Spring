package com.migracion.rangel.infrastructure.encounterstatus.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.encounterstatus.port.repository.EncounterStatusRepository;
import com.migracion.rangel.infrastructure.encounterstatus.adapters.out.persistence.repositories.EncounterStatusJpaRepository;
import com.migracion.rangel.infrastructure.encounterstatus.adapters.out.persistence.repositories.EncounterStatusRepositoryAdapter;
import com.migracion.rangel.infrastructure.encounterstatus.adapters.out.persistence.mappers.EncounterStatusPersistenceMapper;
import com.migracion.rangel.application.encounterstatus.usecase.RegisterEncounterStatusUseCase;
import com.migracion.rangel.application.encounterstatus.usecase.GetEncounterStatusByIdUseCase;
import com.migracion.rangel.application.encounterstatus.usecase.ListEncounterStatusUseCase;
import com.migracion.rangel.application.encounterstatus.usecase.UpdateEncounterStatusUseCase;
import com.migracion.rangel.application.encounterstatus.usecase.DeleteEncounterStatusUseCase;
@Configuration
public class EncounterStatusBeansConfig {
    @Bean
    public EncounterStatusPersistenceMapper encounterStatusPersistenceMapper() { return new EncounterStatusPersistenceMapper(); }
    @Bean
    public EncounterStatusRepository encounterStatusRepository(EncounterStatusJpaRepository repository, EncounterStatusPersistenceMapper mapper) {
        return new EncounterStatusRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterEncounterStatusUseCase registerEncounterStatusUseCase(EncounterStatusRepository repository) {
        return new RegisterEncounterStatusUseCase(repository);
    }
    @Bean
    public GetEncounterStatusByIdUseCase getEncounterStatusByIdUseCase(EncounterStatusRepository repository) {
        return new GetEncounterStatusByIdUseCase(repository);
    }
    @Bean
    public ListEncounterStatusUseCase listEncounterStatusUseCase(EncounterStatusRepository repository) {
        return new ListEncounterStatusUseCase(repository);
    }
    @Bean
    public UpdateEncounterStatusUseCase updateEncounterStatusUseCase(EncounterStatusRepository repository) {
        return new UpdateEncounterStatusUseCase(repository);
    }
    @Bean
    public DeleteEncounterStatusUseCase deleteEncounterStatusUseCase(EncounterStatusRepository repository) {
        return new DeleteEncounterStatusUseCase(repository);
    }
}
