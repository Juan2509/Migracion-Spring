package com.migracion.rangel.infrastructure.relationshiptype.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.relationshiptype.model.aggregate.RelationshipType;
import com.migracion.rangel.domain.relationshiptype.model.valueobject.RelationshipTypeId;
import com.migracion.rangel.domain.relationshiptype.port.repository.RelationshipTypeRepository;
import com.migracion.rangel.infrastructure.relationshiptype.adapters.out.persistence.mappers.RelationshipTypePersistenceMapper;
public class RelationshipTypeRepositoryAdapter implements RelationshipTypeRepository {
    private final RelationshipTypeJpaRepository repository;
    private final RelationshipTypePersistenceMapper mapper;
    public RelationshipTypeRepositoryAdapter(RelationshipTypeJpaRepository repository, RelationshipTypePersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public RelationshipType save(RelationshipType aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<RelationshipType> findById(RelationshipTypeId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<RelationshipType> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(RelationshipType aggregate) { repository.deleteById(aggregate.id().value()); }
    public boolean existsByDescription(String value) { return repository.existsByDescription(value); }
    public boolean existsByDescriptionAndIdNot(String value, RelationshipTypeId id) {
        return repository.existsByDescriptionAndIdNot(value, id.value());
    }
}
