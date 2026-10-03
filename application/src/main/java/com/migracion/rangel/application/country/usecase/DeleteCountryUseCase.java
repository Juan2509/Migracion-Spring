package com.migracion.rangel.application.country.usecase;
import com.migracion.rangel.domain.country.port.repository.CountryRepository;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
import com.migracion.rangel.application.country.dto.CountryResponse;
import com.migracion.rangel.application.country.exception.CountryNotFoundApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.country.event.CountryDeletedEvent;
public class DeleteCountryUseCase {
    private final CountryRepository repository;
    public DeleteCountryUseCase(CountryRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public CountryDeletedEvent execute(CountryId id) {
        var country = repository.findById(id).orElseThrow(() -> new CountryNotFoundApplicationException(id));
        repository.delete(country);
        return new CountryDeletedEvent(id, LocalDateTime.now());
    }
}
