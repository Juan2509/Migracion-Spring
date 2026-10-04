package com.migracion.rangel.infrastructure.treatmentplan.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.treatmentplan.port.repository.TreatmentPlanRepository;
import com.migracion.rangel.domain.encounter.port.repository.EncounterRepository;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.domain.treatmentstatus.port.repository.TreatmentStatusRepository;
import com.migracion.rangel.infrastructure.treatmentplan.adapters.out.persistence.repositories.*;
import com.migracion.rangel.infrastructure.treatmentplan.adapters.out.persistence.mappers.TreatmentPlanPersistenceMapper;
import com.migracion.rangel.application.treatmentplan.usecase.*;
@Configuration
public class TreatmentPlanBeansConfig {
    @Bean public TreatmentPlanPersistenceMapper treatmentPlanPersistenceMapper() { return new TreatmentPlanPersistenceMapper(); }
    @Bean public TreatmentPlanRepository treatmentPlanRepository(TreatmentPlanJpaRepository repository, TreatmentPlanPersistenceMapper mapper) {
        return new TreatmentPlanRepositoryAdapter(repository, mapper);
    }
    @Bean public RegisterTreatmentPlanUseCase registerTreatmentPlanUseCase(TreatmentPlanRepository repository, EncounterRepository encounters, ProfessionalRepository professionals, TreatmentStatusRepository statuses) {
        return new RegisterTreatmentPlanUseCase(repository, encounters, professionals, statuses);
    }
    @Bean public GetTreatmentPlanByIdUseCase getTreatmentPlanByIdUseCase(TreatmentPlanRepository repository) {
        return new GetTreatmentPlanByIdUseCase(repository);
    }
    @Bean public ListTreatmentPlanUseCase listTreatmentPlanUseCase(TreatmentPlanRepository repository) {
        return new ListTreatmentPlanUseCase(repository);
    }
    @Bean public UpdateTreatmentPlanUseCase updateTreatmentPlanUseCase(TreatmentPlanRepository repository, EncounterRepository encounters, ProfessionalRepository professionals, TreatmentStatusRepository statuses) {
        return new UpdateTreatmentPlanUseCase(repository, encounters, professionals, statuses);
    }
    @Bean public DeleteTreatmentPlanUseCase deleteTreatmentPlanUseCase(TreatmentPlanRepository repository) {
        return new DeleteTreatmentPlanUseCase(repository);
    }
}


