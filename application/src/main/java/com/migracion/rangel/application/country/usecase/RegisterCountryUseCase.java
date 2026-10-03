package com.migracion.rangel.application.country.usecase;
import com.migracion.rangel.domain.country.port.repository.CountryRepository;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
import com.migracion.rangel.application.country.dto.CountryResponse;
import com.migracion.rangel.application.country.exception.CountryNotFoundApplicationException;
import com.migracion.rangel.application.country.command.RegisterCountryCommand;
import com.migracion.rangel.domain.country.model.aggregate.Country;
public class RegisterCountryUseCase {
    private final CountryRepository repository;
    public RegisterCountryUseCase(CountryRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public CountryResponse execute(RegisterCountryCommand command) {
        return CountryResponse.from(repository.save(Country.register(command.nameCountry(), command.codeCountry(), command.description(), command.isActive(), command.telephonePrefix())));
    }
}
