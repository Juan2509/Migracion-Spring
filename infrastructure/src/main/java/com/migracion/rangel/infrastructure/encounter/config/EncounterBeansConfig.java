package com.migracion.rangel.infrastructure.encounter.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.encounter.port.repository.EncounterRepository;
import com.migracion.rangel.domain.clinicalrecord.port.repository.ClinicalRecordRepository;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.domain.encountertype.port.repository.EncounterTypeRepository;
import com.migracion.rangel.domain.encountermodality.port.repository.EncounterModalityRepository;
import com.migracion.rangel.domain.encounterstatus.port.repository.EncounterStatusRepository;
import com.migracion.rangel.infrastructure.encounter.adapters.out.persistence.repositories.*;
import com.migracion.rangel.infrastructure.encounter.adapters.out.persistence.mappers.EncounterPersistenceMapper;
import com.migracion.rangel.application.encounter.usecase.*;
@Configuration
public class EncounterBeansConfig {
    @Bean public EncounterPersistenceMapper encounterPersistenceMapper() { return new EncounterPersistenceMapper(); }
    @Bean public EncounterRepository encounterRepository(EncounterJpaRepository repository, EncounterPersistenceMapper mapper) {
        return new EncounterRepositoryAdapter(repository, mapper);
    }
    @Bean public RegisterEncounterUseCase registerEncounterUseCase(EncounterRepository repository, ClinicalRecordRepository records, ProfessionalRepository professionals, EncounterTypeRepository types, EncounterModalityRepository modalities, EncounterStatusRepository statuses) {
        return new RegisterEncounterUseCase(repository, records, professionals, types, modalities, statuses);
    }
    @Bean public GetEncounterByIdUseCase getEncounterByIdUseCase(EncounterRepository repository) {
        return new GetEncounterByIdUseCase(repository);
    }
    @Bean public ListEncounterUseCase listEncounterUseCase(EncounterRepository repository) {
        return new ListEncounterUseCase(repository);
    }
    @Bean public UpdateEncounterUseCase updateEncounterUseCase(EncounterRepository repository, ClinicalRecordRepository records, ProfessionalRepository professionals, EncounterTypeRepository types, EncounterModalityRepository modalities, EncounterStatusRepository statuses) {
        return new UpdateEncounterUseCase(repository, records, professionals, types, modalities, statuses);
    }
    @Bean public DeleteEncounterUseCase deleteEncounterUseCase(EncounterRepository repository) {
        return new DeleteEncounterUseCase(repository);
    }
}
