package com.migracion.rangel.application.country.usecase;
import com.migracion.rangel.domain.country.port.repository.CountryRepository;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
import com.migracion.rangel.application.country.dto.CountryResponse;
import com.migracion.rangel.application.country.exception.CountryNotFoundApplicationException;

public class GetCountryByIdUseCase {
    private final CountryRepository repository;
    public GetCountryByIdUseCase(CountryRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public CountryResponse execute(CountryId id) { return CountryResponse.from(repository.findById(id).orElseThrow(() -> new CountryNotFoundApplicationException(id))); }
}
