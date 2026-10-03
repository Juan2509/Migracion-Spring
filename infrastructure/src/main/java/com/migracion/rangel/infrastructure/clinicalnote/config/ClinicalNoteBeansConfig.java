package com.migracion.rangel.infrastructure.clinicalnote.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.clinicalnote.port.repository.ClinicalNoteRepository;
import com.migracion.rangel.domain.encounter.port.repository.EncounterRepository;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.infrastructure.clinicalnote.adapters.out.persistence.repositories.*;
import com.migracion.rangel.infrastructure.clinicalnote.adapters.out.persistence.mappers.ClinicalNotePersistenceMapper;
import com.migracion.rangel.application.clinicalnote.usecase.*;
@Configuration
public class ClinicalNoteBeansConfig {
    @Bean public ClinicalNotePersistenceMapper clinicalNotePersistenceMapper() { return new ClinicalNotePersistenceMapper(); }
    @Bean public ClinicalNoteRepository clinicalNoteRepository(ClinicalNoteJpaRepository repository, ClinicalNotePersistenceMapper mapper) {
        return new ClinicalNoteRepositoryAdapter(repository, mapper);
    }
    @Bean public RegisterClinicalNoteUseCase registerClinicalNoteUseCase(ClinicalNoteRepository repository, EncounterRepository encounters, ProfessionalRepository professionals) {
        return new RegisterClinicalNoteUseCase(repository, encounters, professionals);
    }
    @Bean public GetClinicalNoteByIdUseCase getClinicalNoteByIdUseCase(ClinicalNoteRepository repository) {
        return new GetClinicalNoteByIdUseCase(repository);
    }
    @Bean public ListClinicalNoteUseCase listClinicalNoteUseCase(ClinicalNoteRepository repository) {
        return new ListClinicalNoteUseCase(repository);
    }
    @Bean public UpdateClinicalNoteUseCase updateClinicalNoteUseCase(ClinicalNoteRepository repository, EncounterRepository encounters, ProfessionalRepository professionals) {
        return new UpdateClinicalNoteUseCase(repository, encounters, professionals);
    }
    @Bean public DeleteClinicalNoteUseCase deleteClinicalNoteUseCase(ClinicalNoteRepository repository) {
        return new DeleteClinicalNoteUseCase(repository);
    }
}

