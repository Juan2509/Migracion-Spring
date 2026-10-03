package com.migracion.rangel.infrastructure.relationshiptype.adapters.out.persistence.repositories;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.migracion.rangel.infrastructure.relationshiptype.adapters.out.persistence.entity.RelationshipTypeJpaEntity;
public interface RelationshipTypeJpaRepository extends JpaRepository<RelationshipTypeJpaEntity, UUID> {
    boolean existsByDescription(String value);
    boolean existsByDescriptionAndIdNot(String value, UUID id);
}
