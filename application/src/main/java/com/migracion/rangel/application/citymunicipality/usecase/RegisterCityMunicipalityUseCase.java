package com.migracion.rangel.application.citymunicipality.usecase;
import com.migracion.rangel.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.application.citymunicipality.dto.CityMunicipalityResponse;
import com.migracion.rangel.application.citymunicipality.exception.CityMunicipalityNotFoundApplicationException;
import com.migracion.rangel.domain.stateregion.port.repository.StateRegionRepository;
import com.migracion.rangel.application.stateregion.exception.StateRegionNotFoundApplicationException;
import com.migracion.rangel.application.citymunicipality.command.RegisterCityMunicipalityCommand;
import com.migracion.rangel.domain.citymunicipality.model.aggregate.CityMunicipality;
public class RegisterCityMunicipalityUseCase {
    private final CityMunicipalityRepository repository;
    private final StateRegionRepository regions;
    public RegisterCityMunicipalityUseCase(CityMunicipalityRepository repository, StateRegionRepository regions) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.regions = java.util.Objects.requireNonNull(regions);
    }
    public CityMunicipalityResponse execute(RegisterCityMunicipalityCommand command) {
        regions.findById(command.regionId()).orElseThrow(() -> new StateRegionNotFoundApplicationException(command.regionId()));
        return CityMunicipalityResponse.from(repository.save(CityMunicipality.register(command.nameCity(), command.codeCiti(), command.description(), command.isActive(), command.regionId())));
    }
}
