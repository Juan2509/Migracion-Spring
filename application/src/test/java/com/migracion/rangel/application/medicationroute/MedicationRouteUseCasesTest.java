package com.migracion.rangel.application.medicationroute;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.medicationroute.command.*;
import com.migracion.rangel.application.medicationroute.exception.MedicationRouteNotFoundApplicationException;
import com.migracion.rangel.application.medicationroute.exception.DuplicateMedicationRouteApplicationException;
import com.migracion.rangel.application.medicationroute.usecase.*;
import com.migracion.rangel.domain.medicationroute.model.aggregate.MedicationRoute;
import com.migracion.rangel.domain.medicationroute.model.valueobject.MedicationRouteId;
import com.migracion.rangel.domain.medicationroute.port.repository.MedicationRouteRepository;

class MedicationRouteUseCasesTest {
    @Test
    void differentCodesAllowRepeatedNames() {
        var repository = new MemoryRepository();
        var register = new RegisterMedicationRouteUseCase(repository);
        var first = register.execute(new RegisterMedicationRouteCommand("FIRST", "Nombre", true));
        var second = register.execute(new RegisterMedicationRouteCommand("SECOND", "Nombre", true));
        assertNotEquals(first.id(), second.id());
        assertEquals(first.name(), second.name());
        assertEquals(2, repository.findAll().size());
    }

    @Test
    void crudPreservesAuditAndReportsMissingRecord() {
        var repository = new MemoryRepository();
        var register = new RegisterMedicationRouteUseCase(repository);
        var get = new GetMedicationRouteByIdUseCase(repository);
        var list = new ListMedicationRouteUseCase(repository);
        var update = new UpdateMedicationRouteUseCase(repository);
        var delete = new DeleteMedicationRouteUseCase(repository);
        var response = register.execute(new RegisterMedicationRouteCommand("CC", "Cédula", true));
        var id = new MedicationRouteId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        var changed = update.execute(new UpdateMedicationRouteCommand(id, "Otro code", "Otro name", false));
        assertEquals(response.id(), changed.id());
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals("Otro code", changed.code());
        assertEquals("Otro name", changed.name());
        assertEquals(false, changed.active());
        assertEquals(changed, get.execute(id));
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(MedicationRouteNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(MedicationRouteNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(MedicationRouteNotFoundApplicationException.class, () -> update.execute(new UpdateMedicationRouteCommand(id, "CC", "Cédula", true)));
    }

    @Test
    void uniquenessRejectsDuplicatesWithoutChangingRecordsAndAllowsOwnValue() {
        var repository = new MemoryRepository();
        var register = new RegisterMedicationRouteUseCase(repository);
        var update = new UpdateMedicationRouteUseCase(repository);
        var first = register.execute(new RegisterMedicationRouteCommand("CC", "Cédula", true));
        var second = register.execute(new RegisterMedicationRouteCommand("Otro code", "Otro name", false));
        assertThrows(DuplicateMedicationRouteApplicationException.class,
                () -> register.execute(new RegisterMedicationRouteCommand("CC", "Cédula", true)));
        var firstId = new MedicationRouteId(first.id());
        var secondId = new MedicationRouteId(second.id());
        var unchanged = update.execute(new UpdateMedicationRouteCommand(firstId, "CC", "Cédula", true));
        assertEquals(first.code(), unchanged.code());
        assertThrows(DuplicateMedicationRouteApplicationException.class,
                () -> update.execute(new UpdateMedicationRouteCommand(secondId, first.code(), first.name(), first.active())));
        assertEquals(second, new GetMedicationRouteByIdUseCase(repository).execute(secondId));
        assertEquals(2, repository.findAll().size());
        assertEquals(3, repository.saves);
    }

    private static class MemoryRepository implements MedicationRouteRepository {
        private final Map<MedicationRouteId, MedicationRoute> values = new LinkedHashMap<>();
        private int saves;
        public MedicationRoute save(MedicationRoute aggregate) { saves++; values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<MedicationRoute> findById(MedicationRouteId id) { return Optional.ofNullable(values.get(id)); }
        public List<MedicationRoute> findAll() { return List.copyOf(values.values()); }
        public void delete(MedicationRoute aggregate) { values.remove(aggregate.id()); }
        public boolean existsByCode(String value) {
            return values.values().stream().anyMatch(aggregate -> aggregate.code().equals(value));
        }
        public boolean existsByCodeAndIdNot(String value, MedicationRouteId id) {
            return values.values().stream().anyMatch(aggregate -> !aggregate.id().equals(id) && aggregate.code().equals(value));
        }
    }
}
