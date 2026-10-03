package com.migracion.rangel.infrastructure.citymunicipality.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.citymunicipality.model.aggregate.CityMunicipality;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import com.migracion.rangel.infrastructure.citymunicipality.adapters.out.persistence.mappers.CityMunicipalityPersistenceMapper;

public class CityMunicipalityRepositoryAdapter implements CityMunicipalityRepository {
    private final CityMunicipalityJpaRepository repository;
    private final CityMunicipalityPersistenceMapper mapper;
    public CityMunicipalityRepositoryAdapter(CityMunicipalityJpaRepository repository, CityMunicipalityPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public CityMunicipality save(CityMunicipality city) { return mapper.toDomain(repository.save(mapper.toJpa(city))); }
    public Optional<CityMunicipality> findById(CityMunicipalityId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<CityMunicipality> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(CityMunicipality city) { repository.deleteById(city.id().value()); }
}
