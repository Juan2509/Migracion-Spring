package com.migracion.rangel.application.professionaltype;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.professionaltype.command.*;
import com.migracion.rangel.application.professionaltype.exception.ProfessionalTypeNotFoundApplicationException;
import com.migracion.rangel.application.professionaltype.exception.DuplicateProfessionalTypeApplicationException;
import com.migracion.rangel.application.professionaltype.usecase.*;
import com.migracion.rangel.domain.professionaltype.model.aggregate.ProfessionalType;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.domain.professionaltype.port.repository.ProfessionalTypeRepository;

class ProfessionalTypeUseCasesTest {
    @Test
    void crudPreservesAuditAndReportsMissingRecord() {
        var repository = new MemoryRepository();
        var register = new RegisterProfessionalTypeUseCase(repository);
        var get = new GetProfessionalTypeByIdUseCase(repository);
        var list = new ListProfessionalTypeUseCase(repository);
        var update = new UpdateProfessionalTypeUseCase(repository);
        var delete = new DeleteProfessionalTypeUseCase(repository);
        var response = register.execute(new RegisterProfessionalTypeCommand("Psicólogo"));
        var id = new ProfessionalTypeId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        var changed = update.execute(new UpdateProfessionalTypeCommand(id, "Otro name"));
        assertEquals(response.id(), changed.id());
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals("Otro name", changed.name());
        assertEquals(changed, get.execute(id));
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(ProfessionalTypeNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(ProfessionalTypeNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(ProfessionalTypeNotFoundApplicationException.class, () -> update.execute(new UpdateProfessionalTypeCommand(id, "Psicólogo")));
    }

    @Test
    void uniquenessRejectsDuplicatesWithoutChangingRecordsAndAllowsOwnValue() {
        var repository = new MemoryRepository();
        var register = new RegisterProfessionalTypeUseCase(repository);
        var update = new UpdateProfessionalTypeUseCase(repository);
        var first = register.execute(new RegisterProfessionalTypeCommand("Psicólogo"));
        var second = register.execute(new RegisterProfessionalTypeCommand("Otro name"));
        assertThrows(DuplicateProfessionalTypeApplicationException.class,
                () -> register.execute(new RegisterProfessionalTypeCommand("Psicólogo")));
        var firstId = new ProfessionalTypeId(first.id());
        var secondId = new ProfessionalTypeId(second.id());
        var unchanged = update.execute(new UpdateProfessionalTypeCommand(firstId, "Psicólogo"));
        assertEquals(first.name(), unchanged.name());
        assertThrows(DuplicateProfessionalTypeApplicationException.class,
                () -> update.execute(new UpdateProfessionalTypeCommand(secondId, first.name())));
        assertEquals(second, new GetProfessionalTypeByIdUseCase(repository).execute(secondId));
        assertEquals(2, repository.findAll().size());
        assertEquals(3, repository.saves);
    }

    private static class MemoryRepository implements ProfessionalTypeRepository {
        private final Map<ProfessionalTypeId, ProfessionalType> values = new LinkedHashMap<>();
        private int saves;
        public ProfessionalType save(ProfessionalType aggregate) { saves++; values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<ProfessionalType> findById(ProfessionalTypeId id) { return Optional.ofNullable(values.get(id)); }
        public List<ProfessionalType> findAll() { return List.copyOf(values.values()); }
        public void delete(ProfessionalType aggregate) { values.remove(aggregate.id()); }
        public boolean existsByName(String value) {
            return values.values().stream().anyMatch(aggregate -> aggregate.name().equals(value));
        }
        public boolean existsByNameAndIdNot(String value, ProfessionalTypeId id) {
            return values.values().stream().anyMatch(aggregate -> !aggregate.id().equals(id) && aggregate.name().equals(value));
        }
    }
}
