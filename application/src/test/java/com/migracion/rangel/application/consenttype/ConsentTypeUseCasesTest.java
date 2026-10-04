package com.migracion.rangel.application.consenttype;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.consenttype.command.*;
import com.migracion.rangel.application.consenttype.exception.ConsentTypeNotFoundApplicationException;
import com.migracion.rangel.application.consenttype.usecase.*;
import com.migracion.rangel.domain.consenttype.model.aggregate.ConsentType;
import com.migracion.rangel.domain.consenttype.model.valueobject.ConsentTypeId;
import com.migracion.rangel.domain.consenttype.port.repository.ConsentTypeRepository;

class ConsentTypeUseCasesTest {
    @Test
    void crudPreservesAuditAndReportsMissingRecord() {
        var repository = new MemoryRepository();
        var register = new RegisterConsentTypeUseCase(repository);
        var get = new GetConsentTypeByIdUseCase(repository);
        var list = new ListConsentTypeUseCase(repository);
        var update = new UpdateConsentTypeUseCase(repository);
        var delete = new DeleteConsentTypeUseCase(repository);
        var response = register.execute(new RegisterConsentTypeCommand("CC", "Cédula", true, "Descripción"));
        var id = new ConsentTypeId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        var changed = update.execute(new UpdateConsentTypeCommand(id, "Otro code", "Otro name", false, "Otra descripción"));
        assertEquals(response.id(), changed.id());
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals("Otro code", changed.code());
        assertEquals("Otro name", changed.name());
        assertEquals(false, changed.active());
        assertEquals(changed, get.execute(id));
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(ConsentTypeNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(ConsentTypeNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(ConsentTypeNotFoundApplicationException.class, () -> update.execute(new UpdateConsentTypeCommand(id, "CC", "Cédula", true, "Descripción")));
    }

    @Test
    void repeatedCodesAndNamesAreAllowed() {
        var repository = new MemoryRepository();
        var register = new RegisterConsentTypeUseCase(repository);
        var first = register.execute(new RegisterConsentTypeCommand("CC", "Nombre", true, "Primera"));
        var second = register.execute(new RegisterConsentTypeCommand("CC", "Nombre", true, "Segunda"));
        assertNotEquals(first.id(), second.id());
        var changed = new UpdateConsentTypeUseCase(repository).execute(
                new UpdateConsentTypeCommand(new ConsentTypeId(second.id()), "CC", "Nombre", false, "Actualizada"));
        assertEquals("Actualizada", changed.description());
        assertEquals(2, repository.findAll().size());
    }

    private static class MemoryRepository implements ConsentTypeRepository {
        private final Map<ConsentTypeId, ConsentType> values = new LinkedHashMap<>();
        private int saves;
        public ConsentType save(ConsentType aggregate) { saves++; values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<ConsentType> findById(ConsentTypeId id) { return Optional.ofNullable(values.get(id)); }
        public List<ConsentType> findAll() { return List.copyOf(values.values()); }
        public void delete(ConsentType aggregate) { values.remove(aggregate.id()); }
    }
}
