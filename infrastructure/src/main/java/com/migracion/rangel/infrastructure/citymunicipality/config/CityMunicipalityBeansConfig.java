package com.migracion.rangel.infrastructure.citymunicipality.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.migracion.rangel.domain.stateregion.port.repository.StateRegionRepository;
import com.migracion.rangel.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import com.migracion.rangel.infrastructure.citymunicipality.adapters.out.persistence.repositories.CityMunicipalityJpaRepository;
import com.migracion.rangel.infrastructure.citymunicipality.adapters.out.persistence.repositories.CityMunicipalityRepositoryAdapter;
import com.migracion.rangel.infrastructure.citymunicipality.adapters.out.persistence.mappers.CityMunicipalityPersistenceMapper;
import com.migracion.rangel.application.citymunicipality.usecase.RegisterCityMunicipalityUseCase;
import com.migracion.rangel.application.citymunicipality.usecase.GetCityMunicipalityByIdUseCase;
import com.migracion.rangel.application.citymunicipality.usecase.ListCityMunicipalityUseCase;
import com.migracion.rangel.application.citymunicipality.usecase.UpdateCityMunicipalityUseCase;
import com.migracion.rangel.application.citymunicipality.usecase.DeleteCityMunicipalityUseCase;

@Configuration
public class CityMunicipalityBeansConfig {
    @Bean
    public CityMunicipalityPersistenceMapper cityMunicipalityPersistenceMapper() { return new CityMunicipalityPersistenceMapper(); }
    @Bean
    public CityMunicipalityRepository cityMunicipalityRepository(CityMunicipalityJpaRepository repository, CityMunicipalityPersistenceMapper mapper) {
        return new CityMunicipalityRepositoryAdapter(repository, mapper);
    }
    @Bean
    public RegisterCityMunicipalityUseCase registerCityMunicipalityUseCase(CityMunicipalityRepository repository, StateRegionRepository regions) {
        return new RegisterCityMunicipalityUseCase(repository, regions);
    }
    @Bean
    public GetCityMunicipalityByIdUseCase getCityMunicipalityByIdUseCase(CityMunicipalityRepository repository) {
        return new GetCityMunicipalityByIdUseCase(repository);
    }
    @Bean
    public ListCityMunicipalityUseCase listCityMunicipalityUseCase(CityMunicipalityRepository repository) {
        return new ListCityMunicipalityUseCase(repository);
    }
    @Bean
    public UpdateCityMunicipalityUseCase updateCityMunicipalityUseCase(CityMunicipalityRepository repository, StateRegionRepository regions) {
        return new UpdateCityMunicipalityUseCase(repository, regions);
    }
    @Bean
    public DeleteCityMunicipalityUseCase deleteCityMunicipalityUseCase(CityMunicipalityRepository repository) {
        return new DeleteCityMunicipalityUseCase(repository);
    }
}
