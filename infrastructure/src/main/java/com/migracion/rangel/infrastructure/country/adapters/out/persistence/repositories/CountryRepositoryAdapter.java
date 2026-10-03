package com.migracion.rangel.infrastructure.country.adapters.out.persistence.repositories;

import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.country.model.aggregate.Country;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
import com.migracion.rangel.domain.country.port.repository.CountryRepository;
import com.migracion.rangel.infrastructure.country.adapters.out.persistence.mappers.CountryPersistenceMapper;

public class CountryRepositoryAdapter implements CountryRepository {
    private final CountryJpaRepository repository;
    private final CountryPersistenceMapper mapper;

    public CountryRepositoryAdapter(CountryJpaRepository repository, CountryPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Country save(Country country) { return mapper.toDomain(repository.save(mapper.toJpa(country))); }
    public Optional<Country> findById(CountryId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<Country> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(Country country) { repository.deleteById(country.id().value()); }
}
