package com.migracion.rangel.application.messagetype;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.messagetype.command.*;
import com.migracion.rangel.application.messagetype.exception.MessageTypeNotFoundApplicationException;
import com.migracion.rangel.application.messagetype.usecase.*;
import com.migracion.rangel.domain.messagetype.model.aggregate.MessageType;
import com.migracion.rangel.domain.messagetype.model.valueobject.MessageTypeId;
import com.migracion.rangel.domain.messagetype.port.repository.MessageTypeRepository;

class MessageTypeUseCasesTest {
    @Test
    void crudPreservesAuditAndReportsMissingMessageType() {
        var repository = new MemoryRepository();
        var register = new RegisterMessageTypeUseCase(repository);
        var get = new GetMessageTypeByIdUseCase(repository);
        var list = new ListMessageTypeUseCase(repository);
        var update = new UpdateMessageTypeUseCase(repository);
        var delete = new DeleteMessageTypeUseCase(repository);
        var response = register.execute(new RegisterMessageTypeCommand("Psicología"));
        var id = new MessageTypeId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        var changed = update.execute(new UpdateMessageTypeCommand(id, "Psiquiatría"));
        assertEquals(response.id(), changed.id());
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals("Psiquiatría", changed.nameType());
        assertEquals(changed, get.execute(id));
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(MessageTypeNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(MessageTypeNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(MessageTypeNotFoundApplicationException.class, () -> update.execute(new UpdateMessageTypeCommand(id, "Psicología")));
    }
    @Test
    void repeatedNamesAreAllowedOnRegisterAndUpdate() {
        var repository = new MemoryRepository();
        var register = new RegisterMessageTypeUseCase(repository);
        var first = register.execute(new RegisterMessageTypeCommand("Psicología"));
        var second = register.execute(new RegisterMessageTypeCommand("Psicología"));
        assertNotEquals(first.id(), second.id());
        var third = register.execute(new RegisterMessageTypeCommand("Medicina"));
        var changed = new UpdateMessageTypeUseCase(repository).execute(
                new UpdateMessageTypeCommand(new MessageTypeId(third.id()), "Psicología"));
        assertEquals("Psicología", changed.nameType());
        assertEquals(3, new ListMessageTypeUseCase(repository).execute().size());
    }
    private static class MemoryRepository implements MessageTypeRepository {
        private final Map<MessageTypeId, MessageType> values = new LinkedHashMap<>();
        public MessageType save(MessageType aggregate) { values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<MessageType> findById(MessageTypeId id) { return Optional.ofNullable(values.get(id)); }
        public List<MessageType> findAll() { return List.copyOf(values.values()); }
        public void delete(MessageType aggregate) { values.remove(aggregate.id()); }
    }
}
