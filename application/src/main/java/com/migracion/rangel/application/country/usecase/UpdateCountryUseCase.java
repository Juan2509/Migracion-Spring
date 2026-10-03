package com.migracion.rangel.application.country.usecase;
import com.migracion.rangel.domain.country.port.repository.CountryRepository;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
import com.migracion.rangel.application.country.dto.CountryResponse;
import com.migracion.rangel.application.country.exception.CountryNotFoundApplicationException;
import com.migracion.rangel.application.country.command.UpdateCountryCommand;
public class UpdateCountryUseCase {
    private final CountryRepository repository;
    public UpdateCountryUseCase(CountryRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public CountryResponse execute(UpdateCountryCommand command) {
        var id = command.id();
        var country = repository.findById(id).orElseThrow(() -> new CountryNotFoundApplicationException(id));
        country.update(command.nameCountry(), command.codeCountry(), command.description(), command.isActive(), command.telephonePrefix());
        return CountryResponse.from(repository.save(country));
    }
}
