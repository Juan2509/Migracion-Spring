package com.migracion.rangel.application.chatairunerror;
import java.lang.reflect.Proxy;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.chatairunerror.command.*;
import com.migracion.rangel.application.chatairunerror.usecase.*;
import com.migracion.rangel.application.chatairunerror.exception.ChatAiRunErrorNotFoundApplicationException;
import com.migracion.rangel.application.chatairun.exception.ChatAiRunNotFoundApplicationException;
import com.migracion.rangel.domain.chatairunerror.model.aggregate.ChatAiRunError;
import com.migracion.rangel.domain.chatairunerror.model.valueobject.ChatAiRunErrorId;
import com.migracion.rangel.domain.chatairunerror.port.repository.ChatAiRunErrorRepository;
import com.migracion.rangel.domain.chatairun.model.aggregate.ChatAiRun;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
import com.migracion.rangel.domain.chatairun.port.repository.ChatAiRunRepository;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.chatmessage.model.valueobject.ChatMessageId;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
class ChatAiRunErrorUseCasesTest {
    private final ChatAiRun run = ChatAiRun.register(ChatConversationId.generate(),ChatMessageId.generate(),AiModelId.generate(),AiRunStatusId.generate());
    private final ChatAiRunRepository runs = (ChatAiRunRepository) Proxy.newProxyInstance(ChatAiRunRepository.class.getClassLoader(),new Class<?>[]{ChatAiRunRepository.class},(proxy,method,args) -> {
        if (method.getName().equals("findById")) { return run.id().equals(args[0]) ? Optional.of(run) : Optional.empty(); }
        throw new UnsupportedOperationException(method.getName());
    });
    @Test
    void crudAllowsRepeatedErrorsAndPreservesCreatedAt() {
        var repository = new MemoryRepository();
        var register = new RegisterChatAiRunErrorUseCase(repository,runs);
        var command = new RegisterChatAiRunErrorCommand(run.id(),"Error","E1","P1");
        var first = register.execute(command);
        var second = register.execute(command);
        assertNotEquals(first.id(),second.id());
        var id = new ChatAiRunErrorId(first.id());
        var update = new UpdateChatAiRunErrorUseCase(repository,runs);
        var changed = update.execute(new UpdateChatAiRunErrorCommand(id,run.id(),"Nuevo","E2","P2"));
        assertEquals(first.createdAt(),changed.createdAt());
        assertEquals("Nuevo",changed.errorMessage());
        assertEquals(changed,new GetChatAiRunErrorByIdUseCase(repository).execute(id));
        assertEquals(2,new ListChatAiRunErrorUseCase(repository).execute().size());
        assertEquals(id,new DeleteChatAiRunErrorUseCase(repository).execute(id).id());
        assertThrows(ChatAiRunErrorNotFoundApplicationException.class, () -> new GetChatAiRunErrorByIdUseCase(repository).execute(id));
        assertThrows(ChatAiRunErrorNotFoundApplicationException.class, () -> new DeleteChatAiRunErrorUseCase(repository).execute(id));
        assertThrows(ChatAiRunErrorNotFoundApplicationException.class, () -> update.execute(new UpdateChatAiRunErrorCommand(id,run.id(),"e","c","p")));
    }
    @Test
    void missingRunAndInvalidLengthPreventSavingOrChangingRecord() {
        var repository = new MemoryRepository();
        var register = new RegisterChatAiRunErrorUseCase(repository,runs);
        assertThrows(ChatAiRunNotFoundApplicationException.class, () -> register.execute(new RegisterChatAiRunErrorCommand(ChatAiRunId.generate(),"e","c","p")));
        assertEquals(0,repository.saves);
        var first = register.execute(new RegisterChatAiRunErrorCommand(run.id(),"Error","E1","P1"));
        var id = new ChatAiRunErrorId(first.id());
        var update = new UpdateChatAiRunErrorUseCase(repository,runs);
        assertThrows(ChatAiRunNotFoundApplicationException.class, () -> update.execute(new UpdateChatAiRunErrorCommand(id,ChatAiRunId.generate(),"Nuevo","c","p")));
        assertThrows(IllegalArgumentException.class, () -> update.execute(new UpdateChatAiRunErrorCommand(id,run.id(),"Nuevo","c".repeat(81),"p")));
        assertEquals(first,new GetChatAiRunErrorByIdUseCase(repository).execute(id));
        assertEquals(1,repository.saves);
    }
    private static class MemoryRepository implements ChatAiRunErrorRepository {
        private final Map<ChatAiRunErrorId,ChatAiRunError> values = new LinkedHashMap<>();
        private int saves;
        public ChatAiRunError save(ChatAiRunError value) { saves++; values.put(value.id(),value); return value; }
        public Optional<ChatAiRunError> findById(ChatAiRunErrorId id) { return Optional.ofNullable(values.get(id)); }
        public List<ChatAiRunError> findAll() { return List.copyOf(values.values()); }
        public void delete(ChatAiRunError value) { values.remove(value.id()); }
    }
}
