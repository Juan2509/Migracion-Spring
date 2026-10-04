package com.migracion.rangel.application.diagnosticsystem;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.diagnosticsystem.command.*;
import com.migracion.rangel.application.diagnosticsystem.exception.DiagnosticSystemNotFoundApplicationException;
import com.migracion.rangel.application.diagnosticsystem.exception.DuplicateDiagnosticSystemApplicationException;
import com.migracion.rangel.application.diagnosticsystem.usecase.*;
import com.migracion.rangel.domain.diagnosticsystem.model.aggregate.DiagnosticSystem;
import com.migracion.rangel.domain.diagnosticsystem.model.valueobject.DiagnosticSystemId;
import com.migracion.rangel.domain.diagnosticsystem.port.repository.DiagnosticSystemRepository;

class DiagnosticSystemUseCasesTest {
    @Test
    void differentCodesAllowRepeatedNames() {
        var repository = new MemoryRepository();
        var register = new RegisterDiagnosticSystemUseCase(repository);
        var first = register.execute(new RegisterDiagnosticSystemCommand("FIRST", "Nombre", true, "Descripción"));
        var second = register.execute(new RegisterDiagnosticSystemCommand("SECOND", "Nombre", true, "Descripción"));
        assertNotEquals(first.id(), second.id());
        assertEquals(first.name(), second.name());
        assertEquals(2, repository.findAll().size());
    }

    @Test
    void crudPreservesAuditAndReportsMissingRecord() {
        var repository = new MemoryRepository();
        var register = new RegisterDiagnosticSystemUseCase(repository);
        var get = new GetDiagnosticSystemByIdUseCase(repository);
        var list = new ListDiagnosticSystemUseCase(repository);
        var update = new UpdateDiagnosticSystemUseCase(repository);
        var delete = new DeleteDiagnosticSystemUseCase(repository);
        var response = register.execute(new RegisterDiagnosticSystemCommand("CC", "Cédula", true, "Descripción"));
        var id = new DiagnosticSystemId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        var changed = update.execute(new UpdateDiagnosticSystemCommand(id, "Otro code", "Otro name", false, "Otra descripción"));
        assertEquals(response.id(), changed.id());
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals("Otro code", changed.code());
        assertEquals("Otro name", changed.name());
        assertEquals(false, changed.active());
        assertEquals(changed, get.execute(id));
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(DiagnosticSystemNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(DiagnosticSystemNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(DiagnosticSystemNotFoundApplicationException.class, () -> update.execute(new UpdateDiagnosticSystemCommand(id, "CC", "Cédula", true, "Descripción")));
    }

    @Test
    void uniquenessRejectsDuplicatesWithoutChangingRecordsAndAllowsOwnValue() {
        var repository = new MemoryRepository();
        var register = new RegisterDiagnosticSystemUseCase(repository);
        var update = new UpdateDiagnosticSystemUseCase(repository);
        var first = register.execute(new RegisterDiagnosticSystemCommand("CC", "Cédula", true, "Descripción"));
        var second = register.execute(new RegisterDiagnosticSystemCommand("Otro code", "Otro name", false, "Otra descripción"));
        assertThrows(DuplicateDiagnosticSystemApplicationException.class,
                () -> register.execute(new RegisterDiagnosticSystemCommand("CC", "Cédula", true, "Descripción")));
        var firstId = new DiagnosticSystemId(first.id());
        var secondId = new DiagnosticSystemId(second.id());
        var unchanged = update.execute(new UpdateDiagnosticSystemCommand(firstId, "CC", "Cédula", true, "Descripción"));
        assertEquals(first.code(), unchanged.code());
        assertThrows(DuplicateDiagnosticSystemApplicationException.class,
                () -> update.execute(new UpdateDiagnosticSystemCommand(secondId, first.code(), first.name(), first.active(), first.version())));
        assertEquals(second, new GetDiagnosticSystemByIdUseCase(repository).execute(secondId));
        assertEquals(2, repository.findAll().size());
        assertEquals(3, repository.saves);
    }

    private static class MemoryRepository implements DiagnosticSystemRepository {
        private final Map<DiagnosticSystemId, DiagnosticSystem> values = new LinkedHashMap<>();
        private int saves;
        public DiagnosticSystem save(DiagnosticSystem aggregate) { saves++; values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<DiagnosticSystem> findById(DiagnosticSystemId id) { return Optional.ofNullable(values.get(id)); }
        public List<DiagnosticSystem> findAll() { return List.copyOf(values.values()); }
        public void delete(DiagnosticSystem aggregate) { values.remove(aggregate.id()); }
        public boolean existsByCode(String value) {
            return values.values().stream().anyMatch(aggregate -> aggregate.code().equals(value));
        }
        public boolean existsByCodeAndIdNot(String value, DiagnosticSystemId id) {
            return values.values().stream().anyMatch(aggregate -> !aggregate.id().equals(id) && aggregate.code().equals(value));
        }
    }
}
