package com.migracion.rangel.application.citymunicipality.usecase;
import com.migracion.rangel.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.application.citymunicipality.dto.CityMunicipalityResponse;
import com.migracion.rangel.application.citymunicipality.exception.CityMunicipalityNotFoundApplicationException;

import java.util.List;
public class ListCityMunicipalityUseCase {
    private final CityMunicipalityRepository repository;

    public ListCityMunicipalityUseCase(CityMunicipalityRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public List<CityMunicipalityResponse> execute() { return repository.findAll().stream().map(CityMunicipalityResponse::from).toList(); }
}
