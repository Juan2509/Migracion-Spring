package com.migracion.rangel.infrastructure.providermodelai.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.providermodelai.port.repository.ProviderModelAiRepository;
import com.migracion.rangel.infrastructure.providermodelai.adapters.out.persistence.repositories.ProviderModelAiJpaRepository;
import com.migracion.rangel.infrastructure.providermodelai.adapters.out.persistence.repositories.ProviderModelAiRepositoryAdapter;
import com.migracion.rangel.infrastructure.providermodelai.adapters.out.persistence.mappers.ProviderModelAiPersistenceMapper;
import com.migracion.rangel.application.providermodelai.usecase.RegisterProviderModelAiUseCase;
import com.migracion.rangel.application.providermodelai.usecase.GetProviderModelAiByIdUseCase;
import com.migracion.rangel.application.providermodelai.usecase.ListProviderModelAiUseCase;
import com.migracion.rangel.application.providermodelai.usecase.UpdateProviderModelAiUseCase;
import com.migracion.rangel.application.providermodelai.usecase.DeleteProviderModelAiUseCase;
@Configuration
public class ProviderModelAiBeansConfig {
    @Bean
    public ProviderModelAiPersistenceMapper providerModelAiPersistenceMapper() { return new ProviderModelAiPersistenceMapper(); }
    @Bean
    public ProviderModelAiRepository providerModelAiRepository(ProviderModelAiJpaRepository repository, ProviderModelAiPersistenceMapper mapper) {
        return new ProviderModelAiRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterProviderModelAiUseCase registerProviderModelAiUseCase(ProviderModelAiRepository repository) {
        return new RegisterProviderModelAiUseCase(repository);
    }
    @Bean
    public GetProviderModelAiByIdUseCase getProviderModelAiByIdUseCase(ProviderModelAiRepository repository) {
        return new GetProviderModelAiByIdUseCase(repository);
    }
    @Bean
    public ListProviderModelAiUseCase listProviderModelAiUseCase(ProviderModelAiRepository repository) {
        return new ListProviderModelAiUseCase(repository);
    }
    @Bean
    public UpdateProviderModelAiUseCase updateProviderModelAiUseCase(ProviderModelAiRepository repository) {
        return new UpdateProviderModelAiUseCase(repository);
    }
    @Bean
    public DeleteProviderModelAiUseCase deleteProviderModelAiUseCase(ProviderModelAiRepository repository) {
        return new DeleteProviderModelAiUseCase(repository);
    }
}

