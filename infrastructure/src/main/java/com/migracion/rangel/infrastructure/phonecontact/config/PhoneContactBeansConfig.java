package com.migracion.rangel.infrastructure.phonecontact.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.contact.port.repository.ContactRepository;
import com.migracion.rangel.domain.phonecontact.port.repository.PhoneContactRepository;
import com.migracion.rangel.infrastructure.phonecontact.adapters.out.persistence.repositories.PhoneContactJpaRepository;
import com.migracion.rangel.infrastructure.phonecontact.adapters.out.persistence.repositories.PhoneContactRepositoryAdapter;
import com.migracion.rangel.infrastructure.phonecontact.adapters.out.persistence.mappers.PhoneContactPersistenceMapper;
import com.migracion.rangel.application.phonecontact.usecase.RegisterPhoneContactUseCase;
import com.migracion.rangel.application.phonecontact.usecase.GetPhoneContactByIdUseCase;
import com.migracion.rangel.application.phonecontact.usecase.ListPhoneContactUseCase;
import com.migracion.rangel.application.phonecontact.usecase.UpdatePhoneContactUseCase;
import com.migracion.rangel.application.phonecontact.usecase.DeletePhoneContactUseCase;
@Configuration
public class PhoneContactBeansConfig {
    @Bean
    public PhoneContactPersistenceMapper phoneContactPersistenceMapper() { return new PhoneContactPersistenceMapper(); }
    @Bean
    public PhoneContactRepository phoneContactRepository(PhoneContactJpaRepository repository, PhoneContactPersistenceMapper mapper) {
        return new PhoneContactRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterPhoneContactUseCase registerPhoneContactUseCase(PhoneContactRepository repository, ContactRepository contacts) {
        return new RegisterPhoneContactUseCase(repository, contacts);
    }
    @Bean
    public GetPhoneContactByIdUseCase getPhoneContactByIdUseCase(PhoneContactRepository repository) {
        return new GetPhoneContactByIdUseCase(repository);
    }
    @Bean
    public ListPhoneContactUseCase listPhoneContactUseCase(PhoneContactRepository repository) {
        return new ListPhoneContactUseCase(repository);
    }
    @Bean
    public UpdatePhoneContactUseCase updatePhoneContactUseCase(PhoneContactRepository repository, ContactRepository contacts) {
        return new UpdatePhoneContactUseCase(repository, contacts);
    }
    @Bean
    public DeletePhoneContactUseCase deletePhoneContactUseCase(PhoneContactRepository repository) {
        return new DeletePhoneContactUseCase(repository);
    }
}
