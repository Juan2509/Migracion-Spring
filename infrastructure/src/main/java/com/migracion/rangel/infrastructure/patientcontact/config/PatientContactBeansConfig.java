package com.migracion.rangel.infrastructure.patientcontact.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.patientcontact.port.repository.PatientContactRepository;
import com.migracion.rangel.domain.contact.port.repository.ContactRepository;
import com.migracion.rangel.domain.patient.port.repository.PatientRepository;
import com.migracion.rangel.domain.relationshiptype.port.repository.RelationshipTypeRepository;
import com.migracion.rangel.infrastructure.patientcontact.adapters.out.persistence.repositories.*;
import com.migracion.rangel.infrastructure.patientcontact.adapters.out.persistence.mappers.PatientContactPersistenceMapper;
import com.migracion.rangel.application.patientcontact.usecase.*;
@Configuration
public class PatientContactBeansConfig {
    @Bean public PatientContactPersistenceMapper patientContactPersistenceMapper() { return new PatientContactPersistenceMapper(); }
    @Bean public PatientContactRepository patientContactRepository(PatientContactJpaRepository repository, PatientContactPersistenceMapper mapper) {
        return new PatientContactRepositoryAdapter(repository, mapper);
    }
    @Bean public RegisterPatientContactUseCase registerPatientContactUseCase(PatientContactRepository repository, ContactRepository contacts, PatientRepository patients, RelationshipTypeRepository relationships) {
        return new RegisterPatientContactUseCase(repository, contacts, patients, relationships);
    }
    @Bean public GetPatientContactByIdUseCase getPatientContactByIdUseCase(PatientContactRepository repository) {
        return new GetPatientContactByIdUseCase(repository);
    }
    @Bean public ListPatientContactUseCase listPatientContactUseCase(PatientContactRepository repository) {
        return new ListPatientContactUseCase(repository);
    }
    @Bean public UpdatePatientContactUseCase updatePatientContactUseCase(PatientContactRepository repository, ContactRepository contacts, PatientRepository patients, RelationshipTypeRepository relationships) {
        return new UpdatePatientContactUseCase(repository, contacts, patients, relationships);
    }
    @Bean public DeletePatientContactUseCase deletePatientContactUseCase(PatientContactRepository repository) {
        return new DeletePatientContactUseCase(repository);
    }
}
