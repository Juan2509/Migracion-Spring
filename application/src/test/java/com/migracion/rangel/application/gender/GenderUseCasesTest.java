package com.migracion.rangel.application.gender;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.gender.command.*;
import com.migracion.rangel.application.gender.exception.GenderNotFoundApplicationException;
import com.migracion.rangel.application.gender.exception.DuplicateGenderApplicationException;
import com.migracion.rangel.application.gender.usecase.*;
import com.migracion.rangel.domain.gender.model.aggregate.Gender;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
import com.migracion.rangel.domain.gender.port.repository.GenderRepository;

class GenderUseCasesTest {
    @Test
    void crudPreservesAuditAndReportsMissingRecord() {
        var repository = new MemoryRepository();
        var register = new RegisterGenderUseCase(repository);
        var get = new GetGenderByIdUseCase(repository);
        var list = new ListGenderUseCase(repository);
        var update = new UpdateGenderUseCase(repository);
        var delete = new DeleteGenderUseCase(repository);
        var response = register.execute(new RegisterGenderCommand("Femenino"));
        var id = new GenderId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        var changed = update.execute(new UpdateGenderCommand(id, "Otro description"));
        assertEquals(response.id(), changed.id());
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals("Otro description", changed.description());
        assertEquals(changed, get.execute(id));
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(GenderNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(GenderNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(GenderNotFoundApplicationException.class, () -> update.execute(new UpdateGenderCommand(id, "Femenino")));
    }

    @Test
    void uniquenessRejectsDuplicatesWithoutChangingRecordsAndAllowsOwnValue() {
        var repository = new MemoryRepository();
        var register = new RegisterGenderUseCase(repository);
        var update = new UpdateGenderUseCase(repository);
        var first = register.execute(new RegisterGenderCommand("Femenino"));
        var second = register.execute(new RegisterGenderCommand("Otro description"));
        assertThrows(DuplicateGenderApplicationException.class,
                () -> register.execute(new RegisterGenderCommand("Femenino")));
        var firstId = new GenderId(first.id());
        var secondId = new GenderId(second.id());
        var unchanged = update.execute(new UpdateGenderCommand(firstId, "Femenino"));
        assertEquals(first.description(), unchanged.description());
        assertThrows(DuplicateGenderApplicationException.class,
                () -> update.execute(new UpdateGenderCommand(secondId, first.description())));
        assertEquals(second, new GetGenderByIdUseCase(repository).execute(secondId));
        assertEquals(2, repository.findAll().size());
        assertEquals(3, repository.saves);
    }

    private static class MemoryRepository implements GenderRepository {
        private final Map<GenderId, Gender> values = new LinkedHashMap<>();
        private int saves;
        public Gender save(Gender aggregate) { saves++; values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<Gender> findById(GenderId id) { return Optional.ofNullable(values.get(id)); }
        public List<Gender> findAll() { return List.copyOf(values.values()); }
        public void delete(Gender aggregate) { values.remove(aggregate.id()); }
        public boolean existsByDescription(String value) {
            return values.values().stream().anyMatch(aggregate -> aggregate.description().equals(value));
        }
        public boolean existsByDescriptionAndIdNot(String value, GenderId id) {
            return values.values().stream().anyMatch(aggregate -> !aggregate.id().equals(id) && aggregate.description().equals(value));
        }
    }
}
