package com.migracion.rangel.infrastructure.patient.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.patient.port.repository.PatientRepository;
import com.migracion.rangel.domain.documenttype.port.repository.DocumentTypeRepository;
import com.migracion.rangel.domain.gender.port.repository.GenderRepository;
import com.migracion.rangel.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.infrastructure.patient.adapters.out.persistence.repositories.*;
import com.migracion.rangel.infrastructure.patient.adapters.out.persistence.mappers.PatientPersistenceMapper;
import com.migracion.rangel.application.patient.usecase.*;
@Configuration
public class PatientBeansConfig {
    @Bean public PatientPersistenceMapper patientPersistenceMapper() { return new PatientPersistenceMapper(); }
    @Bean public PatientRepository patientRepository(PatientJpaRepository repository, PatientPersistenceMapper mapper) {
        return new PatientRepositoryAdapter(repository, mapper);
    }
    @Bean public RegisterPatientUseCase registerPatientUseCase(PatientRepository repository, DocumentTypeRepository documents, GenderRepository genders, CityMunicipalityRepository cities, ProfessionalRepository professionals) {
        return new RegisterPatientUseCase(repository, documents, genders, cities, professionals);
    }
    @Bean public GetPatientByIdUseCase getPatientByIdUseCase(PatientRepository repository) {
        return new GetPatientByIdUseCase(repository);
    }
    @Bean public ListPatientUseCase listPatientUseCase(PatientRepository repository) {
        return new ListPatientUseCase(repository);
    }
    @Bean public UpdatePatientUseCase updatePatientUseCase(PatientRepository repository, DocumentTypeRepository documents, GenderRepository genders, CityMunicipalityRepository cities, ProfessionalRepository professionals) {
        return new UpdatePatientUseCase(repository, documents, genders, cities, professionals);
    }
    @Bean public DeletePatientUseCase deletePatientUseCase(PatientRepository repository) {
        return new DeletePatientUseCase(repository);
    }
}
