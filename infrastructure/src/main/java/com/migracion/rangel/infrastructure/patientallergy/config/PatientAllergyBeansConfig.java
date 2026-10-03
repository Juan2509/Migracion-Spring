package com.migracion.rangel.infrastructure.patientallergy.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.patientallergy.port.repository.PatientAllergyRepository;
import com.migracion.rangel.domain.patient.port.repository.PatientRepository;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.infrastructure.patientallergy.adapters.out.persistence.repositories.*;
import com.migracion.rangel.infrastructure.patientallergy.adapters.out.persistence.mappers.PatientAllergyPersistenceMapper;
import com.migracion.rangel.application.patientallergy.usecase.*;
@Configuration
public class PatientAllergyBeansConfig {
    @Bean public PatientAllergyPersistenceMapper patientAllergyPersistenceMapper() { return new PatientAllergyPersistenceMapper(); }
    @Bean public PatientAllergyRepository patientAllergyRepository(PatientAllergyJpaRepository repository, PatientAllergyPersistenceMapper mapper) {
        return new PatientAllergyRepositoryAdapter(repository, mapper);
    }
    @Bean public RegisterPatientAllergyUseCase registerPatientAllergyUseCase(PatientAllergyRepository repository, PatientRepository patients, ProfessionalRepository professionals) {
        return new RegisterPatientAllergyUseCase(repository, patients, professionals);
    }
    @Bean public GetPatientAllergyByIdUseCase getPatientAllergyByIdUseCase(PatientAllergyRepository repository) {
        return new GetPatientAllergyByIdUseCase(repository);
    }
    @Bean public ListPatientAllergyUseCase listPatientAllergyUseCase(PatientAllergyRepository repository) {
        return new ListPatientAllergyUseCase(repository);
    }
    @Bean public UpdatePatientAllergyUseCase updatePatientAllergyUseCase(PatientAllergyRepository repository, PatientRepository patients, ProfessionalRepository professionals) {
        return new UpdatePatientAllergyUseCase(repository, patients, professionals);
    }
    @Bean public DeletePatientAllergyUseCase deletePatientAllergyUseCase(PatientAllergyRepository repository) {
        return new DeletePatientAllergyUseCase(repository);
    }
}
