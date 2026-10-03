package com.migracion.rangel.application.citymunicipality.usecase;
import com.migracion.rangel.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.application.citymunicipality.dto.CityMunicipalityResponse;
import com.migracion.rangel.application.citymunicipality.exception.CityMunicipalityNotFoundApplicationException;

import java.time.LocalDateTime;
import com.migracion.rangel.domain.citymunicipality.event.CityMunicipalityDeletedEvent;
public class DeleteCityMunicipalityUseCase {
    private final CityMunicipalityRepository repository;

    public DeleteCityMunicipalityUseCase(CityMunicipalityRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public CityMunicipalityDeletedEvent execute(CityMunicipalityId id) {
        var city = repository.findById(id).orElseThrow(() -> new CityMunicipalityNotFoundApplicationException(id));
        repository.delete(city);
        return new CityMunicipalityDeletedEvent(id, LocalDateTime.now());
    }
}
