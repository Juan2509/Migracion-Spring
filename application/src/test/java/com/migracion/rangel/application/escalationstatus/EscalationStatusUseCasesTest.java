package com.migracion.rangel.application.escalationstatus;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.escalationstatus.command.*;
import com.migracion.rangel.application.escalationstatus.exception.EscalationStatusNotFoundApplicationException;
import com.migracion.rangel.application.escalationstatus.usecase.*;
import com.migracion.rangel.domain.escalationstatus.model.aggregate.EscalationStatus;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import com.migracion.rangel.domain.escalationstatus.port.repository.EscalationStatusRepository;

class EscalationStatusUseCasesTest {
    @Test
    void crudPreservesAuditAndReportsMissingEscalationStatus() {
        var repository = new MemoryRepository();
        var register = new RegisterEscalationStatusUseCase(repository);
        var get = new GetEscalationStatusByIdUseCase(repository);
        var list = new ListEscalationStatusUseCase(repository);
        var update = new UpdateEscalationStatusUseCase(repository);
        var delete = new DeleteEscalationStatusUseCase(repository);
        var response = register.execute(new RegisterEscalationStatusCommand("Psicología"));
        var id = new EscalationStatusId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        var changed = update.execute(new UpdateEscalationStatusCommand(id, "Psiquiatría"));
        assertEquals(response.id(), changed.id());
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals("Psiquiatría", changed.nameStatus());
        assertEquals(changed, get.execute(id));
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(EscalationStatusNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(EscalationStatusNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(EscalationStatusNotFoundApplicationException.class, () -> update.execute(new UpdateEscalationStatusCommand(id, "Psicología")));
    }
    @Test
    void repeatedNamesAreAllowedOnRegisterAndUpdate() {
        var repository = new MemoryRepository();
        var register = new RegisterEscalationStatusUseCase(repository);
        var first = register.execute(new RegisterEscalationStatusCommand("Psicología"));
        var second = register.execute(new RegisterEscalationStatusCommand("Psicología"));
        assertNotEquals(first.id(), second.id());
        var third = register.execute(new RegisterEscalationStatusCommand("Medicina"));
        var changed = new UpdateEscalationStatusUseCase(repository).execute(
                new UpdateEscalationStatusCommand(new EscalationStatusId(third.id()), "Psicología"));
        assertEquals("Psicología", changed.nameStatus());
        assertEquals(3, new ListEscalationStatusUseCase(repository).execute().size());
    }
    private static class MemoryRepository implements EscalationStatusRepository {
        private final Map<EscalationStatusId, EscalationStatus> values = new LinkedHashMap<>();
        public EscalationStatus save(EscalationStatus aggregate) { values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<EscalationStatus> findById(EscalationStatusId id) { return Optional.ofNullable(values.get(id)); }
        public List<EscalationStatus> findAll() { return List.copyOf(values.values()); }
        public void delete(EscalationStatus aggregate) { values.remove(aggregate.id()); }
    }
}
