package com.migracion.rangel.application.citymunicipality.usecase;
import com.migracion.rangel.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.application.citymunicipality.dto.CityMunicipalityResponse;
import com.migracion.rangel.application.citymunicipality.exception.CityMunicipalityNotFoundApplicationException;
import com.migracion.rangel.domain.stateregion.port.repository.StateRegionRepository;
import com.migracion.rangel.application.stateregion.exception.StateRegionNotFoundApplicationException;
import com.migracion.rangel.application.citymunicipality.command.UpdateCityMunicipalityCommand;
public class UpdateCityMunicipalityUseCase {
    private final CityMunicipalityRepository repository;
    private final StateRegionRepository regions;
    public UpdateCityMunicipalityUseCase(CityMunicipalityRepository repository, StateRegionRepository regions) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.regions = java.util.Objects.requireNonNull(regions);
    }
    public CityMunicipalityResponse execute(UpdateCityMunicipalityCommand command) {
        var id = command.id();
        var city = repository.findById(id).orElseThrow(() -> new CityMunicipalityNotFoundApplicationException(id));
        regions.findById(command.regionId()).orElseThrow(() -> new StateRegionNotFoundApplicationException(command.regionId()));
        city.update(command.nameCity(), command.codeCiti(), command.description(), command.isActive(), command.regionId());
        return CityMunicipalityResponse.from(repository.save(city));
    }
}
