package com.migracion.rangel.infrastructure.treatmentstatus.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.treatmentstatus.port.repository.TreatmentStatusRepository;
import com.migracion.rangel.infrastructure.treatmentstatus.adapters.out.persistence.repositories.TreatmentStatusJpaRepository;
import com.migracion.rangel.infrastructure.treatmentstatus.adapters.out.persistence.repositories.TreatmentStatusRepositoryAdapter;
import com.migracion.rangel.infrastructure.treatmentstatus.adapters.out.persistence.mappers.TreatmentStatusPersistenceMapper;
import com.migracion.rangel.application.treatmentstatus.usecase.RegisterTreatmentStatusUseCase;
import com.migracion.rangel.application.treatmentstatus.usecase.GetTreatmentStatusByIdUseCase;
import com.migracion.rangel.application.treatmentstatus.usecase.ListTreatmentStatusUseCase;
import com.migracion.rangel.application.treatmentstatus.usecase.UpdateTreatmentStatusUseCase;
import com.migracion.rangel.application.treatmentstatus.usecase.DeleteTreatmentStatusUseCase;
@Configuration
public class TreatmentStatusBeansConfig {
    @Bean
    public TreatmentStatusPersistenceMapper treatmentStatusPersistenceMapper() { return new TreatmentStatusPersistenceMapper(); }
    @Bean
    public TreatmentStatusRepository treatmentStatusRepository(TreatmentStatusJpaRepository repository, TreatmentStatusPersistenceMapper mapper) {
        return new TreatmentStatusRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterTreatmentStatusUseCase registerTreatmentStatusUseCase(TreatmentStatusRepository repository) {
        return new RegisterTreatmentStatusUseCase(repository);
    }
    @Bean
    public GetTreatmentStatusByIdUseCase getTreatmentStatusByIdUseCase(TreatmentStatusRepository repository) {
        return new GetTreatmentStatusByIdUseCase(repository);
    }
    @Bean
    public ListTreatmentStatusUseCase listTreatmentStatusUseCase(TreatmentStatusRepository repository) {
        return new ListTreatmentStatusUseCase(repository);
    }
    @Bean
    public UpdateTreatmentStatusUseCase updateTreatmentStatusUseCase(TreatmentStatusRepository repository) {
        return new UpdateTreatmentStatusUseCase(repository);
    }
    @Bean
    public DeleteTreatmentStatusUseCase deleteTreatmentStatusUseCase(TreatmentStatusRepository repository) {
        return new DeleteTreatmentStatusUseCase(repository);
    }
}

