package com.migracion.rangel.infrastructure.contact.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.contact.port.repository.ContactRepository;
import com.migracion.rangel.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.infrastructure.contact.adapters.out.persistence.repositories.ContactJpaRepository;
import com.migracion.rangel.infrastructure.contact.adapters.out.persistence.repositories.ContactRepositoryAdapter;
import com.migracion.rangel.infrastructure.contact.adapters.out.persistence.mappers.ContactPersistenceMapper;
import com.migracion.rangel.application.contact.usecase.RegisterContactUseCase;
import com.migracion.rangel.application.contact.usecase.GetContactByIdUseCase;
import com.migracion.rangel.application.contact.usecase.ListContactUseCase;
import com.migracion.rangel.application.contact.usecase.UpdateContactUseCase;
import com.migracion.rangel.application.contact.usecase.DeleteContactUseCase;
@Configuration
public class ContactBeansConfig {
    @Bean
    public ContactPersistenceMapper contactPersistenceMapper() { return new ContactPersistenceMapper(); }
    @Bean
    public ContactRepository contactRepository(ContactJpaRepository repository, ContactPersistenceMapper mapper) {
        return new ContactRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterContactUseCase registerContactUseCase(ContactRepository repository, CityMunicipalityRepository cities, ProfessionalRepository professionals) {
        return new RegisterContactUseCase(repository, cities, professionals);
    }
    @Bean
    public GetContactByIdUseCase getContactByIdUseCase(ContactRepository repository) {
        return new GetContactByIdUseCase(repository);
    }
    @Bean
    public ListContactUseCase listContactUseCase(ContactRepository repository) {
        return new ListContactUseCase(repository);
    }
    @Bean
    public UpdateContactUseCase updateContactUseCase(ContactRepository repository, CityMunicipalityRepository cities, ProfessionalRepository professionals) {
        return new UpdateContactUseCase(repository, cities, professionals);
    }
    @Bean
    public DeleteContactUseCase deleteContactUseCase(ContactRepository repository) {
        return new DeleteContactUseCase(repository);
    }
}
