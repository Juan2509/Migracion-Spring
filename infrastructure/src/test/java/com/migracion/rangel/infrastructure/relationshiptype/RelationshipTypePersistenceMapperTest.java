package com.migracion.rangel.infrastructure.relationshiptype;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.relationshiptype.model.aggregate.RelationshipType;
import com.migracion.rangel.infrastructure.relationshiptype.adapters.out.persistence.mappers.RelationshipTypePersistenceMapper;
import com.migracion.rangel.infrastructure.relationshiptype.adapters.out.persistence.entity.RelationshipTypeJpaEntity;

class RelationshipTypePersistenceMapperTest {
    @Test
    void mapsOnlyTheTwoSqlColumnsAndRestoresWithoutEvents() {
        var original = RelationshipType.register("Madre");
        var mapper = new RelationshipTypePersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(original));
        assertEquals(original.id(), restored.id());
        assertEquals(original.description(), restored.description());
        assertTrue(restored.domainEvents().isEmpty());
        assertEquals(2, RelationshipTypeJpaEntity.class.getDeclaredFields().length);
    }
}
