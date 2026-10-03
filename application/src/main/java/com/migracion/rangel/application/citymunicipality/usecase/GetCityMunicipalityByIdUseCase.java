package com.migracion.rangel.application.citymunicipality.usecase;
import com.migracion.rangel.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.application.citymunicipality.dto.CityMunicipalityResponse;
import com.migracion.rangel.application.citymunicipality.exception.CityMunicipalityNotFoundApplicationException;


public class GetCityMunicipalityByIdUseCase {
    private final CityMunicipalityRepository repository;

    public GetCityMunicipalityByIdUseCase(CityMunicipalityRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public CityMunicipalityResponse execute(CityMunicipalityId id) { return CityMunicipalityResponse.from(repository.findById(id).orElseThrow(() -> new CityMunicipalityNotFoundApplicationException(id))); }
}
