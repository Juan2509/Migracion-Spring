package com.migracion.rangel.domain.country.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.country.model.aggregate.Country;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
public interface CountryRepository {
    Country save(Country country);
    Optional<Country> findById(CountryId id);
    List<Country> findAll();
    void delete(Country country);
}
