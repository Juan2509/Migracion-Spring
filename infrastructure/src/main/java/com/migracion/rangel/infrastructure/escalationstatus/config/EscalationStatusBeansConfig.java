package com.migracion.rangel.infrastructure.escalationstatus.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.escalationstatus.port.repository.EscalationStatusRepository;
import com.migracion.rangel.infrastructure.escalationstatus.adapters.out.persistence.repositories.EscalationStatusJpaRepository;
import com.migracion.rangel.infrastructure.escalationstatus.adapters.out.persistence.repositories.EscalationStatusRepositoryAdapter;
import com.migracion.rangel.infrastructure.escalationstatus.adapters.out.persistence.mappers.EscalationStatusPersistenceMapper;
import com.migracion.rangel.application.escalationstatus.usecase.RegisterEscalationStatusUseCase;
import com.migracion.rangel.application.escalationstatus.usecase.GetEscalationStatusByIdUseCase;
import com.migracion.rangel.application.escalationstatus.usecase.ListEscalationStatusUseCase;
import com.migracion.rangel.application.escalationstatus.usecase.UpdateEscalationStatusUseCase;
import com.migracion.rangel.application.escalationstatus.usecase.DeleteEscalationStatusUseCase;
@Configuration
public class EscalationStatusBeansConfig {
    @Bean
    public EscalationStatusPersistenceMapper escalationstatusPersistenceMapper() { return new EscalationStatusPersistenceMapper(); }
    @Bean
    public EscalationStatusRepository escalationstatusRepository(EscalationStatusJpaRepository repository, EscalationStatusPersistenceMapper mapper) {
        return new EscalationStatusRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterEscalationStatusUseCase registerEscalationStatusUseCase(EscalationStatusRepository repository) {
        return new RegisterEscalationStatusUseCase(repository);
    }
    @Bean
    public GetEscalationStatusByIdUseCase getEscalationStatusByIdUseCase(EscalationStatusRepository repository) {
        return new GetEscalationStatusByIdUseCase(repository);
    }
    @Bean
    public ListEscalationStatusUseCase listEscalationStatusUseCase(EscalationStatusRepository repository) {
        return new ListEscalationStatusUseCase(repository);
    }
    @Bean
    public UpdateEscalationStatusUseCase updateEscalationStatusUseCase(EscalationStatusRepository repository) {
        return new UpdateEscalationStatusUseCase(repository);
    }
    @Bean
    public DeleteEscalationStatusUseCase deleteEscalationStatusUseCase(EscalationStatusRepository repository) {
        return new DeleteEscalationStatusUseCase(repository);
    }
}
