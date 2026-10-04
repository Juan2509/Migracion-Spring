package com.migracion.rangel.infrastructure.treatmentgoalstatus.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;
import com.migracion.rangel.infrastructure.treatmentgoalstatus.adapters.out.persistence.repositories.TreatmentGoalStatusJpaRepository;
import com.migracion.rangel.infrastructure.treatmentgoalstatus.adapters.out.persistence.repositories.TreatmentGoalStatusRepositoryAdapter;
import com.migracion.rangel.infrastructure.treatmentgoalstatus.adapters.out.persistence.mappers.TreatmentGoalStatusPersistenceMapper;
import com.migracion.rangel.application.treatmentgoalstatus.usecase.RegisterTreatmentGoalStatusUseCase;
import com.migracion.rangel.application.treatmentgoalstatus.usecase.GetTreatmentGoalStatusByIdUseCase;
import com.migracion.rangel.application.treatmentgoalstatus.usecase.ListTreatmentGoalStatusUseCase;
import com.migracion.rangel.application.treatmentgoalstatus.usecase.UpdateTreatmentGoalStatusUseCase;
import com.migracion.rangel.application.treatmentgoalstatus.usecase.DeleteTreatmentGoalStatusUseCase;
@Configuration
public class TreatmentGoalStatusBeansConfig {
    @Bean
    public TreatmentGoalStatusPersistenceMapper treatmentGoalStatusPersistenceMapper() { return new TreatmentGoalStatusPersistenceMapper(); }
    @Bean
    public TreatmentGoalStatusRepository treatmentGoalStatusRepository(TreatmentGoalStatusJpaRepository repository, TreatmentGoalStatusPersistenceMapper mapper) {
        return new TreatmentGoalStatusRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterTreatmentGoalStatusUseCase registerTreatmentGoalStatusUseCase(TreatmentGoalStatusRepository repository) {
        return new RegisterTreatmentGoalStatusUseCase(repository);
    }
    @Bean
    public GetTreatmentGoalStatusByIdUseCase getTreatmentGoalStatusByIdUseCase(TreatmentGoalStatusRepository repository) {
        return new GetTreatmentGoalStatusByIdUseCase(repository);
    }
    @Bean
    public ListTreatmentGoalStatusUseCase listTreatmentGoalStatusUseCase(TreatmentGoalStatusRepository repository) {
        return new ListTreatmentGoalStatusUseCase(repository);
    }
    @Bean
    public UpdateTreatmentGoalStatusUseCase updateTreatmentGoalStatusUseCase(TreatmentGoalStatusRepository repository) {
        return new UpdateTreatmentGoalStatusUseCase(repository);
    }
    @Bean
    public DeleteTreatmentGoalStatusUseCase deleteTreatmentGoalStatusUseCase(TreatmentGoalStatusRepository repository) {
        return new DeleteTreatmentGoalStatusUseCase(repository);
    }
}


