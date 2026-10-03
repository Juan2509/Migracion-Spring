package com.migracion.rangel.infrastructure.stateregion.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.country.port.repository.CountryRepository;
import com.migracion.rangel.domain.stateregion.port.repository.StateRegionRepository;
import com.migracion.rangel.infrastructure.stateregion.adapters.out.persistence.repositories.StateRegionJpaRepository;
import com.migracion.rangel.infrastructure.stateregion.adapters.out.persistence.repositories.StateRegionRepositoryAdapter;
import com.migracion.rangel.infrastructure.stateregion.adapters.out.persistence.mappers.StateRegionPersistenceMapper;
import com.migracion.rangel.application.stateregion.usecase.RegisterStateRegionUseCase;
import com.migracion.rangel.application.stateregion.usecase.GetStateRegionByIdUseCase;
import com.migracion.rangel.application.stateregion.usecase.ListStateRegionUseCase;
import com.migracion.rangel.application.stateregion.usecase.UpdateStateRegionUseCase;
import com.migracion.rangel.application.stateregion.usecase.DeleteStateRegionUseCase;

@Configuration
public class StateRegionBeansConfig {
    @Bean
    public StateRegionPersistenceMapper stateRegionPersistenceMapper() { return new StateRegionPersistenceMapper(); }
    @Bean
    public StateRegionRepository stateRegionRepository(StateRegionJpaRepository repository, StateRegionPersistenceMapper mapper) {
        return new StateRegionRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterStateRegionUseCase registerStateRegionUseCase(StateRegionRepository repository, CountryRepository countries) {
        return new RegisterStateRegionUseCase(repository, countries);
    }
    @Bean
    public GetStateRegionByIdUseCase getStateRegionByIdUseCase(StateRegionRepository repository) {
        return new GetStateRegionByIdUseCase(repository);
    }
    @Bean
    public ListStateRegionUseCase listStateRegionUseCase(StateRegionRepository repository) {
        return new ListStateRegionUseCase(repository);
    }
    @Bean
    public UpdateStateRegionUseCase updateStateRegionUseCase(StateRegionRepository repository, CountryRepository countries) {
        return new UpdateStateRegionUseCase(repository, countries);
    }
    @Bean
    public DeleteStateRegionUseCase deleteStateRegionUseCase(StateRegionRepository repository) {
        return new DeleteStateRegionUseCase(repository);
    }
}
