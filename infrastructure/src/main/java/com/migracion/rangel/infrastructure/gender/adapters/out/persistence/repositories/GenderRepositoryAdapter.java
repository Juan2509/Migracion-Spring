package com.migracion.rangel.infrastructure.gender.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.gender.model.aggregate.Gender;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
import com.migracion.rangel.domain.gender.port.repository.GenderRepository;
import com.migracion.rangel.infrastructure.gender.adapters.out.persistence.mappers.GenderPersistenceMapper;
public class GenderRepositoryAdapter implements GenderRepository {
    private final GenderJpaRepository repository;
    private final GenderPersistenceMapper mapper;
    public GenderRepositoryAdapter(GenderJpaRepository repository, GenderPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public Gender save(Gender aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<Gender> findById(GenderId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<Gender> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(Gender aggregate) { repository.deleteById(aggregate.id().value()); }
    public boolean existsByDescription(String value) { return repository.existsByDescription(value); }
    public boolean existsByDescriptionAndIdNot(String value, GenderId id) {
        return repository.existsByDescriptionAndIdNot(value, id.value());
    }
}
