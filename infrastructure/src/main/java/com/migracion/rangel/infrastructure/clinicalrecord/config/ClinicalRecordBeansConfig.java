package com.migracion.rangel.infrastructure.clinicalrecord.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.clinicalrecord.port.repository.ClinicalRecordRepository;
import com.migracion.rangel.domain.patient.port.repository.PatientRepository;
import com.migracion.rangel.domain.clinicalrecordstatus.port.repository.ClinicalRecordStatusRepository;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.infrastructure.clinicalrecord.adapters.out.persistence.repositories.*;
import com.migracion.rangel.infrastructure.clinicalrecord.adapters.out.persistence.mappers.ClinicalRecordPersistenceMapper;
import com.migracion.rangel.application.clinicalrecord.usecase.*;
@Configuration
public class ClinicalRecordBeansConfig {
    @Bean public ClinicalRecordPersistenceMapper clinicalRecordPersistenceMapper() { return new ClinicalRecordPersistenceMapper(); }
    @Bean public ClinicalRecordRepository clinicalRecordRepository(ClinicalRecordJpaRepository repository, ClinicalRecordPersistenceMapper mapper) {
        return new ClinicalRecordRepositoryAdapter(repository, mapper);
    }
    @Bean public RegisterClinicalRecordUseCase registerClinicalRecordUseCase(ClinicalRecordRepository repository, PatientRepository patients, ClinicalRecordStatusRepository statuses, ProfessionalRepository professionals) {
        return new RegisterClinicalRecordUseCase(repository, patients, statuses, professionals);
    }
    @Bean public GetClinicalRecordByIdUseCase getClinicalRecordByIdUseCase(ClinicalRecordRepository repository) {
        return new GetClinicalRecordByIdUseCase(repository);
    }
    @Bean public ListClinicalRecordUseCase listClinicalRecordUseCase(ClinicalRecordRepository repository) {
        return new ListClinicalRecordUseCase(repository);
    }
    @Bean public UpdateClinicalRecordUseCase updateClinicalRecordUseCase(ClinicalRecordRepository repository, PatientRepository patients, ClinicalRecordStatusRepository statuses) {
        return new UpdateClinicalRecordUseCase(repository, patients, statuses);
    }
    @Bean public DeleteClinicalRecordUseCase deleteClinicalRecordUseCase(ClinicalRecordRepository repository) {
        return new DeleteClinicalRecordUseCase(repository);
    }
}
