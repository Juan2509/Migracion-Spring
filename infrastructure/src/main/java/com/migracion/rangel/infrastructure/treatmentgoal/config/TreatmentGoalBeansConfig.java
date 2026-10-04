package com.migracion.rangel.infrastructure.treatmentgoal.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.treatmentgoal.port.repository.TreatmentGoalRepository;
import com.migracion.rangel.domain.treatmentplan.port.repository.TreatmentPlanRepository;
import com.migracion.rangel.domain.treatmentgoalstatus.port.repository.TreatmentGoalStatusRepository;
import com.migracion.rangel.infrastructure.treatmentgoal.adapters.out.persistence.repositories.*;
import com.migracion.rangel.infrastructure.treatmentgoal.adapters.out.persistence.mappers.TreatmentGoalPersistenceMapper;
import com.migracion.rangel.application.treatmentgoal.usecase.*;
@Configuration
public class TreatmentGoalBeansConfig {
    @Bean public TreatmentGoalPersistenceMapper treatmentGoalPersistenceMapper() { return new TreatmentGoalPersistenceMapper(); }
    @Bean public TreatmentGoalRepository treatmentGoalRepository(TreatmentGoalJpaRepository repository, TreatmentGoalPersistenceMapper mapper) {
        return new TreatmentGoalRepositoryAdapter(repository, mapper);
    }
    @Bean public RegisterTreatmentGoalUseCase registerTreatmentGoalUseCase(TreatmentGoalRepository repository, TreatmentPlanRepository plans, TreatmentGoalStatusRepository statuses) {
        return new RegisterTreatmentGoalUseCase(repository, plans, statuses);
    }
    @Bean public GetTreatmentGoalByIdUseCase getTreatmentGoalByIdUseCase(TreatmentGoalRepository repository) {
        return new GetTreatmentGoalByIdUseCase(repository);
    }
    @Bean public ListTreatmentGoalUseCase listTreatmentGoalUseCase(TreatmentGoalRepository repository) {
        return new ListTreatmentGoalUseCase(repository);
    }
    @Bean public UpdateTreatmentGoalUseCase updateTreatmentGoalUseCase(TreatmentGoalRepository repository, TreatmentPlanRepository plans, TreatmentGoalStatusRepository statuses) {
        return new UpdateTreatmentGoalUseCase(repository, plans, statuses);
    }
    @Bean public DeleteTreatmentGoalUseCase deleteTreatmentGoalUseCase(TreatmentGoalRepository repository) {
        return new DeleteTreatmentGoalUseCase(repository);
    }
}

