package com.migracion.rangel.application.relationshiptype;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.relationshiptype.command.*;
import com.migracion.rangel.application.relationshiptype.exception.*;
import com.migracion.rangel.application.relationshiptype.usecase.*;
import com.migracion.rangel.domain.relationshiptype.model.aggregate.RelationshipType;
import com.migracion.rangel.domain.relationshiptype.model.valueobject.RelationshipTypeId;
import com.migracion.rangel.domain.relationshiptype.port.repository.RelationshipTypeRepository;

class RelationshipTypeUseCasesTest {
    @Test
    void completeCrudAndMissingRecordErrors() {
        var repository = new MemoryRepository();
        var register = new RegisterRelationshipTypeUseCase(repository);
        var get = new GetRelationshipTypeByIdUseCase(repository);
        var list = new ListRelationshipTypeUseCase(repository);
        var update = new UpdateRelationshipTypeUseCase(repository);
        var delete = new DeleteRelationshipTypeUseCase(repository);
        var response = register.execute(new RegisterRelationshipTypeCommand("Madre"));
        var id = new RelationshipTypeId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        var changed = update.execute(new UpdateRelationshipTypeCommand(id, "Padre"));
        assertEquals(response.id(), changed.id());
        assertEquals("Padre", changed.description());
        assertEquals(changed, get.execute(id));
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(RelationshipTypeNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(RelationshipTypeNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(RelationshipTypeNotFoundApplicationException.class,
                () -> update.execute(new UpdateRelationshipTypeCommand(id, "Madre")));
    }
    @Test
    void rejectsDuplicateDescriptionsButAllowsOwnDescription() {
        var repository = new MemoryRepository();
        var register = new RegisterRelationshipTypeUseCase(repository);
        var update = new UpdateRelationshipTypeUseCase(repository);
        var first = register.execute(new RegisterRelationshipTypeCommand("Madre"));
        var second = register.execute(new RegisterRelationshipTypeCommand("Padre"));
        assertThrows(DuplicateRelationshipTypeApplicationException.class,
                () -> register.execute(new RegisterRelationshipTypeCommand("Madre")));
        assertEquals(first, update.execute(new UpdateRelationshipTypeCommand(new RelationshipTypeId(first.id()), "Madre")));
        var secondId = new RelationshipTypeId(second.id());
        assertThrows(DuplicateRelationshipTypeApplicationException.class,
                () -> update.execute(new UpdateRelationshipTypeCommand(secondId, "Madre")));
        assertEquals(second, new GetRelationshipTypeByIdUseCase(repository).execute(secondId));
        assertEquals(3, repository.saves);
    }
    private static class MemoryRepository implements RelationshipTypeRepository {
        private final Map<RelationshipTypeId, RelationshipType> values = new LinkedHashMap<>();
        private int saves;
        public RelationshipType save(RelationshipType aggregate) { saves++; values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<RelationshipType> findById(RelationshipTypeId id) { return Optional.ofNullable(values.get(id)); }
        public List<RelationshipType> findAll() { return List.copyOf(values.values()); }
        public void delete(RelationshipType aggregate) { values.remove(aggregate.id()); }
        public boolean existsByDescription(String value) { return values.values().stream().anyMatch(a -> a.description().equals(value)); }
        public boolean existsByDescriptionAndIdNot(String value, RelationshipTypeId id) {
            return values.values().stream().anyMatch(a -> !a.id().equals(id) && a.description().equals(value));
        }
    }
}
