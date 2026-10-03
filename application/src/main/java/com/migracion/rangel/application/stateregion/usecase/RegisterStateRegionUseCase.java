package com.migracion.rangel.application.stateregion.usecase;
import com.migracion.rangel.domain.stateregion.port.repository.StateRegionRepository;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;
import com.migracion.rangel.application.stateregion.dto.StateRegionResponse;
import com.migracion.rangel.application.stateregion.exception.StateRegionNotFoundApplicationException;
import com.migracion.rangel.domain.country.port.repository.CountryRepository;
import com.migracion.rangel.application.country.exception.CountryNotFoundApplicationException;
import com.migracion.rangel.application.stateregion.command.RegisterStateRegionCommand;
import com.migracion.rangel.domain.stateregion.model.aggregate.StateRegion;
public class RegisterStateRegionUseCase {
    private final StateRegionRepository repository;
    private final CountryRepository countries;
    public RegisterStateRegionUseCase(StateRegionRepository repository, CountryRepository countries) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.countries = java.util.Objects.requireNonNull(countries);
    }
    public StateRegionResponse execute(RegisterStateRegionCommand command) {
        countries.findById(command.countryId()).orElseThrow(() -> new CountryNotFoundApplicationException(command.countryId()));
        return StateRegionResponse.from(repository.save(StateRegion.register(command.nameRegion(), command.codeRegion(), command.description(), command.isActive(), command.countryId())));
    }
}
