package com.migracion.rangel.infrastructure.riskassessment.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.riskassessment.port.repository.RiskAssessmentRepository;
import com.migracion.rangel.domain.encounter.port.repository.EncounterRepository;
import com.migracion.rangel.domain.risklevel.port.repository.RiskLevelRepository;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.infrastructure.riskassessment.adapters.out.persistence.repositories.*;
import com.migracion.rangel.infrastructure.riskassessment.adapters.out.persistence.mappers.RiskAssessmentPersistenceMapper;
import com.migracion.rangel.application.riskassessment.usecase.*;
@Configuration
public class RiskAssessmentBeansConfig {
    @Bean public RiskAssessmentPersistenceMapper riskAssessmentPersistenceMapper() { return new RiskAssessmentPersistenceMapper(); }
    @Bean public RiskAssessmentRepository riskAssessmentRepository(RiskAssessmentJpaRepository repository, RiskAssessmentPersistenceMapper mapper) {
        return new RiskAssessmentRepositoryAdapter(repository, mapper);
    }
    @Bean public RegisterRiskAssessmentUseCase registerRiskAssessmentUseCase(RiskAssessmentRepository repository, EncounterRepository encounters, RiskLevelRepository levels, ProfessionalRepository professionals) {
        return new RegisterRiskAssessmentUseCase(repository, encounters, levels, professionals);
    }
    @Bean public GetRiskAssessmentByIdUseCase getRiskAssessmentByIdUseCase(RiskAssessmentRepository repository) {
        return new GetRiskAssessmentByIdUseCase(repository);
    }
    @Bean public ListRiskAssessmentUseCase listRiskAssessmentUseCase(RiskAssessmentRepository repository) {
        return new ListRiskAssessmentUseCase(repository);
    }
    @Bean public UpdateRiskAssessmentUseCase updateRiskAssessmentUseCase(RiskAssessmentRepository repository, EncounterRepository encounters, RiskLevelRepository levels, ProfessionalRepository professionals) {
        return new UpdateRiskAssessmentUseCase(repository, encounters, levels, professionals);
    }
    @Bean public DeleteRiskAssessmentUseCase deleteRiskAssessmentUseCase(RiskAssessmentRepository repository) {
        return new DeleteRiskAssessmentUseCase(repository);
    }
}
