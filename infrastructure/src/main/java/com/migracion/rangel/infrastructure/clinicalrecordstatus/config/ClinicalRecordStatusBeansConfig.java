package com.migracion.rangel.infrastructure.clinicalrecordstatus.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.clinicalrecordstatus.port.repository.ClinicalRecordStatusRepository;

import com.migracion.rangel.infrastructure.clinicalrecordstatus.adapters.out.persistence.repositories.*;
import com.migracion.rangel.infrastructure.clinicalrecordstatus.adapters.out.persistence.mappers.ClinicalRecordStatusPersistenceMapper;
import com.migracion.rangel.application.clinicalrecordstatus.usecase.*;
@Configuration
public class ClinicalRecordStatusBeansConfig {
    @Bean public ClinicalRecordStatusPersistenceMapper clinicalRecordStatusPersistenceMapper() { return new ClinicalRecordStatusPersistenceMapper(); }
    @Bean public ClinicalRecordStatusRepository clinicalRecordStatusRepository(ClinicalRecordStatusJpaRepository repository, ClinicalRecordStatusPersistenceMapper mapper) {
        return new ClinicalRecordStatusRepositoryAdapter(repository, mapper);
    }
    @Bean public RegisterClinicalRecordStatusUseCase registerClinicalRecordStatusUseCase(ClinicalRecordStatusRepository repository) {
        return new RegisterClinicalRecordStatusUseCase(repository);
    }
    @Bean public GetClinicalRecordStatusByIdUseCase getClinicalRecordStatusByIdUseCase(ClinicalRecordStatusRepository repository) {
        return new GetClinicalRecordStatusByIdUseCase(repository);
    }
    @Bean public ListClinicalRecordStatusUseCase listClinicalRecordStatusUseCase(ClinicalRecordStatusRepository repository) {
        return new ListClinicalRecordStatusUseCase(repository);
    }
    @Bean public UpdateClinicalRecordStatusUseCase updateClinicalRecordStatusUseCase(ClinicalRecordStatusRepository repository) {
        return new UpdateClinicalRecordStatusUseCase(repository);
    }
    @Bean public DeleteClinicalRecordStatusUseCase deleteClinicalRecordStatusUseCase(ClinicalRecordStatusRepository repository) {
        return new DeleteClinicalRecordStatusUseCase(repository);
    }
}
