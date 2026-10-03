package com.migracion.rangel.infrastructure.emailcontact.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.contact.port.repository.ContactRepository;
import com.migracion.rangel.domain.emailcontact.port.repository.EmailContactRepository;
import com.migracion.rangel.infrastructure.emailcontact.adapters.out.persistence.repositories.EmailContactJpaRepository;
import com.migracion.rangel.infrastructure.emailcontact.adapters.out.persistence.repositories.EmailContactRepositoryAdapter;
import com.migracion.rangel.infrastructure.emailcontact.adapters.out.persistence.mappers.EmailContactPersistenceMapper;
import com.migracion.rangel.application.emailcontact.usecase.RegisterEmailContactUseCase;
import com.migracion.rangel.application.emailcontact.usecase.GetEmailContactByIdUseCase;
import com.migracion.rangel.application.emailcontact.usecase.ListEmailContactUseCase;
import com.migracion.rangel.application.emailcontact.usecase.UpdateEmailContactUseCase;
import com.migracion.rangel.application.emailcontact.usecase.DeleteEmailContactUseCase;
@Configuration
public class EmailContactBeansConfig {
    @Bean
    public EmailContactPersistenceMapper emailContactPersistenceMapper() { return new EmailContactPersistenceMapper(); }
    @Bean
    public EmailContactRepository emailContactRepository(EmailContactJpaRepository repository, EmailContactPersistenceMapper mapper) {
        return new EmailContactRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterEmailContactUseCase registerEmailContactUseCase(EmailContactRepository repository, ContactRepository contacts) {
        return new RegisterEmailContactUseCase(repository, contacts);
    }
    @Bean
    public GetEmailContactByIdUseCase getEmailContactByIdUseCase(EmailContactRepository repository) {
        return new GetEmailContactByIdUseCase(repository);
    }
    @Bean
    public ListEmailContactUseCase listEmailContactUseCase(EmailContactRepository repository) {
        return new ListEmailContactUseCase(repository);
    }
    @Bean
    public UpdateEmailContactUseCase updateEmailContactUseCase(EmailContactRepository repository, ContactRepository contacts) {
        return new UpdateEmailContactUseCase(repository, contacts);
    }
    @Bean
    public DeleteEmailContactUseCase deleteEmailContactUseCase(EmailContactRepository repository) {
        return new DeleteEmailContactUseCase(repository);
    }
}
