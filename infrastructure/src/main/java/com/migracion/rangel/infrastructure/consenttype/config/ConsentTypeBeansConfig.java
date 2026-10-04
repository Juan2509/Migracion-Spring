package com.migracion.rangel.infrastructure.consenttype.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.consenttype.port.repository.ConsentTypeRepository;
import com.migracion.rangel.infrastructure.consenttype.adapters.out.persistence.repositories.ConsentTypeJpaRepository;
import com.migracion.rangel.infrastructure.consenttype.adapters.out.persistence.repositories.ConsentTypeRepositoryAdapter;
import com.migracion.rangel.infrastructure.consenttype.adapters.out.persistence.mappers.ConsentTypePersistenceMapper;
import com.migracion.rangel.application.consenttype.usecase.RegisterConsentTypeUseCase;
import com.migracion.rangel.application.consenttype.usecase.GetConsentTypeByIdUseCase;
import com.migracion.rangel.application.consenttype.usecase.ListConsentTypeUseCase;
import com.migracion.rangel.application.consenttype.usecase.UpdateConsentTypeUseCase;
import com.migracion.rangel.application.consenttype.usecase.DeleteConsentTypeUseCase;
@Configuration
public class ConsentTypeBeansConfig {
    @Bean
    public ConsentTypePersistenceMapper consentTypePersistenceMapper() { return new ConsentTypePersistenceMapper(); }
    @Bean
    public ConsentTypeRepository consentTypeRepository(ConsentTypeJpaRepository repository, ConsentTypePersistenceMapper mapper) {
        return new ConsentTypeRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterConsentTypeUseCase registerConsentTypeUseCase(ConsentTypeRepository repository) {
        return new RegisterConsentTypeUseCase(repository);
    }
    @Bean
    public GetConsentTypeByIdUseCase getConsentTypeByIdUseCase(ConsentTypeRepository repository) {
        return new GetConsentTypeByIdUseCase(repository);
    }
    @Bean
    public ListConsentTypeUseCase listConsentTypeUseCase(ConsentTypeRepository repository) {
        return new ListConsentTypeUseCase(repository);
    }
    @Bean
    public UpdateConsentTypeUseCase updateConsentTypeUseCase(ConsentTypeRepository repository) {
        return new UpdateConsentTypeUseCase(repository);
    }
    @Bean
    public DeleteConsentTypeUseCase deleteConsentTypeUseCase(ConsentTypeRepository repository) {
        return new DeleteConsentTypeUseCase(repository);
    }
}

