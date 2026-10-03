package com.migracion.rangel.infrastructure.country.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.country.port.repository.CountryRepository;
import com.migracion.rangel.infrastructure.country.adapters.out.persistence.repositories.CountryJpaRepository;
import com.migracion.rangel.infrastructure.country.adapters.out.persistence.repositories.CountryRepositoryAdapter;
import com.migracion.rangel.infrastructure.country.adapters.out.persistence.mappers.CountryPersistenceMapper;
import com.migracion.rangel.application.country.usecase.RegisterCountryUseCase;
import com.migracion.rangel.application.country.usecase.GetCountryByIdUseCase;
import com.migracion.rangel.application.country.usecase.ListCountryUseCase;
import com.migracion.rangel.application.country.usecase.UpdateCountryUseCase;
import com.migracion.rangel.application.country.usecase.DeleteCountryUseCase;

@Configuration
public class CountryBeansConfig {
    @Bean
    public CountryPersistenceMapper countryPersistenceMapper() { return new CountryPersistenceMapper(); }
    @Bean
    public CountryRepository countryRepository(CountryJpaRepository repository, CountryPersistenceMapper mapper) {
        return new CountryRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterCountryUseCase registerCountryUseCase(CountryRepository repository) {
        return new RegisterCountryUseCase(repository);
    }
    @Bean
    public GetCountryByIdUseCase getCountryByIdUseCase(CountryRepository repository) {
        return new GetCountryByIdUseCase(repository);
    }
    @Bean
    public ListCountryUseCase listCountryUseCase(CountryRepository repository) {
        return new ListCountryUseCase(repository);
    }
    @Bean
    public UpdateCountryUseCase updateCountryUseCase(CountryRepository repository) {
        return new UpdateCountryUseCase(repository);
    }
    @Bean
    public DeleteCountryUseCase deleteCountryUseCase(CountryRepository repository) {
        return new DeleteCountryUseCase(repository);
    }
}
