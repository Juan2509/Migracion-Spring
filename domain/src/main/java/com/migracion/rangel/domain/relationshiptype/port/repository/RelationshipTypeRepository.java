package com.migracion.rangel.domain.relationshiptype.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.relationshiptype.model.aggregate.RelationshipType;
import com.migracion.rangel.domain.relationshiptype.model.valueobject.RelationshipTypeId;
public interface RelationshipTypeRepository {
    RelationshipType save(RelationshipType aggregate);
    Optional<RelationshipType> findById(RelationshipTypeId id);
    List<RelationshipType> findAll();
    void delete(RelationshipType aggregate);
    boolean existsByDescription(String value);
    boolean existsByDescriptionAndIdNot(String value, RelationshipTypeId id);
}
