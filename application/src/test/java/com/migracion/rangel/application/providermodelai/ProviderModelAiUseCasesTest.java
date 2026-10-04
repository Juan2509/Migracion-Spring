package com.migracion.rangel.application.providermodelai;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.providermodelai.command.*;
import com.migracion.rangel.application.providermodelai.exception.ProviderModelAiNotFoundApplicationException;
import com.migracion.rangel.application.providermodelai.usecase.*;
import com.migracion.rangel.domain.providermodelai.model.aggregate.ProviderModelAi;
import com.migracion.rangel.domain.providermodelai.model.valueobject.ProviderModelAiId;
import com.migracion.rangel.domain.providermodelai.port.repository.ProviderModelAiRepository;

class ProviderModelAiUseCasesTest {
    @Test
    void crudPreservesAuditAndReportsMissingRecord() {
        var repository = new MemoryRepository();
        var register = new RegisterProviderModelAiUseCase(repository);
        var get = new GetProviderModelAiByIdUseCase(repository);
        var list = new ListProviderModelAiUseCase(repository);
        var update = new UpdateProviderModelAiUseCase(repository);
        var delete = new DeleteProviderModelAiUseCase(repository);
        var response = register.execute(new RegisterProviderModelAiCommand("CC", "Cédula", true, "Descripción"));
        var id = new ProviderModelAiId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        var changed = update.execute(new UpdateProviderModelAiCommand(id, "Otro nameProviderAi", "Otro razonSocial", false, "Otra descripción"));
        assertEquals(response.id(), changed.id());
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals("Otro nameProviderAi", changed.nameProviderAi());
        assertEquals("Otro razonSocial", changed.razonSocial());
        assertEquals(false, changed.isActive());
        assertEquals(changed, get.execute(id));
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(ProviderModelAiNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(ProviderModelAiNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(ProviderModelAiNotFoundApplicationException.class, () -> update.execute(new UpdateProviderModelAiCommand(id, "CC", "Cédula", true, "Descripción")));
    }

    @Test
    void repeatedCodesAndNamesAreAllowed() {
        var repository = new MemoryRepository();
        var register = new RegisterProviderModelAiUseCase(repository);
        var first = register.execute(new RegisterProviderModelAiCommand("CC", "Nombre", true, "Primera"));
        var second = register.execute(new RegisterProviderModelAiCommand("CC", "Nombre", true, "Segunda"));
        assertNotEquals(first.id(), second.id());
        var changed = new UpdateProviderModelAiUseCase(repository).execute(
                new UpdateProviderModelAiCommand(new ProviderModelAiId(second.id()), "CC", "Nombre", false, "Actualizada"));
        assertEquals("Actualizada", changed.sitioWeb());
        assertEquals(2, repository.findAll().size());
    }

    private static class MemoryRepository implements ProviderModelAiRepository {
        private final Map<ProviderModelAiId, ProviderModelAi> values = new LinkedHashMap<>();
        private int saves;
        public ProviderModelAi save(ProviderModelAi aggregate) { saves++; values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<ProviderModelAi> findById(ProviderModelAiId id) { return Optional.ofNullable(values.get(id)); }
        public List<ProviderModelAi> findAll() { return List.copyOf(values.values()); }
        public void delete(ProviderModelAi aggregate) { values.remove(aggregate.id()); }
    }
}
