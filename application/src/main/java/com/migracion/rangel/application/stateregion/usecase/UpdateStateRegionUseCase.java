package com.migracion.rangel.application.stateregion.usecase;
import com.migracion.rangel.domain.stateregion.port.repository.StateRegionRepository;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;
import com.migracion.rangel.application.stateregion.dto.StateRegionResponse;
import com.migracion.rangel.application.stateregion.exception.StateRegionNotFoundApplicationException;
import com.migracion.rangel.domain.country.port.repository.CountryRepository;
import com.migracion.rangel.application.country.exception.CountryNotFoundApplicationException;
import com.migracion.rangel.application.stateregion.command.UpdateStateRegionCommand;
public class UpdateStateRegionUseCase {
    private final StateRegionRepository repository;
    private final CountryRepository countries;
    public UpdateStateRegionUseCase(StateRegionRepository repository, CountryRepository countries) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.countries = java.util.Objects.requireNonNull(countries);
    }
    public StateRegionResponse execute(UpdateStateRegionCommand command) {
        var id = command.id();
        var region = repository.findById(id).orElseThrow(() -> new StateRegionNotFoundApplicationException(id));
        countries.findById(command.countryId()).orElseThrow(() -> new CountryNotFoundApplicationException(command.countryId()));
        region.update(command.nameRegion(), command.codeRegion(), command.description(), command.isActive(), command.countryId());
        return StateRegionResponse.from(repository.save(region));
    }
}
