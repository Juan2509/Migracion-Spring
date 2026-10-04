package com.migracion.rangel.application.sendertype;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.sendertype.command.*;
import com.migracion.rangel.application.sendertype.exception.SenderTypeNotFoundApplicationException;
import com.migracion.rangel.application.sendertype.usecase.*;
import com.migracion.rangel.domain.sendertype.model.aggregate.SenderType;
import com.migracion.rangel.domain.sendertype.model.valueobject.SenderTypeId;
import com.migracion.rangel.domain.sendertype.port.repository.SenderTypeRepository;

class SenderTypeUseCasesTest {
    @Test
    void crudPreservesAuditAndReportsMissingSenderType() {
        var repository = new MemoryRepository();
        var register = new RegisterSenderTypeUseCase(repository);
        var get = new GetSenderTypeByIdUseCase(repository);
        var list = new ListSenderTypeUseCase(repository);
        var update = new UpdateSenderTypeUseCase(repository);
        var delete = new DeleteSenderTypeUseCase(repository);
        var response = register.execute(new RegisterSenderTypeCommand("Psicología"));
        var id = new SenderTypeId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        var changed = update.execute(new UpdateSenderTypeCommand(id, "Psiquiatría"));
        assertEquals(response.id(), changed.id());
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals("Psiquiatría", changed.nameType());
        assertEquals(changed, get.execute(id));
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(SenderTypeNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(SenderTypeNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(SenderTypeNotFoundApplicationException.class, () -> update.execute(new UpdateSenderTypeCommand(id, "Psicología")));
    }
    @Test
    void repeatedNamesAreAllowedOnRegisterAndUpdate() {
        var repository = new MemoryRepository();
        var register = new RegisterSenderTypeUseCase(repository);
        var first = register.execute(new RegisterSenderTypeCommand("Psicología"));
        var second = register.execute(new RegisterSenderTypeCommand("Psicología"));
        assertNotEquals(first.id(), second.id());
        var third = register.execute(new RegisterSenderTypeCommand("Medicina"));
        var changed = new UpdateSenderTypeUseCase(repository).execute(
                new UpdateSenderTypeCommand(new SenderTypeId(third.id()), "Psicología"));
        assertEquals("Psicología", changed.nameType());
        assertEquals(3, new ListSenderTypeUseCase(repository).execute().size());
    }
    private static class MemoryRepository implements SenderTypeRepository {
        private final Map<SenderTypeId, SenderType> values = new LinkedHashMap<>();
        public SenderType save(SenderType aggregate) { values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<SenderType> findById(SenderTypeId id) { return Optional.ofNullable(values.get(id)); }
        public List<SenderType> findAll() { return List.copyOf(values.values()); }
        public void delete(SenderType aggregate) { values.remove(aggregate.id()); }
    }
}
