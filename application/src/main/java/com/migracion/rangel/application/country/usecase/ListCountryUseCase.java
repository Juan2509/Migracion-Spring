package com.migracion.rangel.application.country.usecase;
import com.migracion.rangel.domain.country.port.repository.CountryRepository;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
import com.migracion.rangel.application.country.dto.CountryResponse;
import com.migracion.rangel.application.country.exception.CountryNotFoundApplicationException;
import java.util.List;
public class ListCountryUseCase {
    private final CountryRepository repository;
    public ListCountryUseCase(CountryRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<CountryResponse> execute() { return repository.findAll().stream().map(CountryResponse::from).toList(); }
}
