package com.migracion.rangel.application.chatescalationstatushistory;
import java.time.LocalDateTime;
import java.lang.reflect.Proxy;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.chatescalationstatushistory.command.*;
import com.migracion.rangel.application.chatescalationstatushistory.usecase.*;
import com.migracion.rangel.application.chatescalationstatushistory.exception.ChatEscalationStatusHistoryNotFoundApplicationException;
import com.migracion.rangel.application.chatescalation.exception.ChatEscalationNotFoundApplicationException;
import com.migracion.rangel.application.escalationstatus.exception.EscalationStatusNotFoundApplicationException;
import com.migracion.rangel.domain.chatescalationstatushistory.model.aggregate.ChatEscalationStatusHistory;
import com.migracion.rangel.domain.chatescalationstatushistory.model.valueobject.ChatEscalationStatusHistoryId;
import com.migracion.rangel.domain.chatescalationstatushistory.port.repository.ChatEscalationStatusHistoryRepository;
import com.migracion.rangel.domain.chatescalation.model.aggregate.ChatEscalation;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import com.migracion.rangel.domain.chatescalation.port.repository.ChatEscalationRepository;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.escalationstatus.model.aggregate.EscalationStatus;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import com.migracion.rangel.domain.escalationstatus.port.repository.EscalationStatusRepository;
class ChatEscalationStatusHistoryUseCasesTest {
    private final ChatEscalation escalation=ChatEscalation.register(ChatConversationId.generate(),"Motivo",EscalationStatusId.generate(),false);
    private final EscalationStatus escalationstatus=EscalationStatus.register("Pendiente");
    private final ChatEscalationRepository escalations=lookup(ChatEscalationRepository.class,Map.of(escalation.id(),escalation));
    private final EscalationStatusRepository statuses=lookup(EscalationStatusRepository.class,Map.of(escalationstatus.id(),escalationstatus));
    private final LocalDateTime date=LocalDateTime.of(2021,2,3,4,5);
    @Test void crudAllowsRepeatedHistoryAndPreservesCreationDate() {
        var repo=new MemoryRepository();
        var command=new RegisterChatEscalationStatusHistoryCommand(escalation.id(),escalationstatus.id(),date);
        var first=register(repo).execute(command);
        var second=register(repo).execute(command);
        assertNotEquals(first.id(),second.id());
        assertEquals(date,first.changedAt());
        var id=new ChatEscalationStatusHistoryId(first.id());
        var next=date.plusDays(3);
        var changed=update(repo).execute(new UpdateChatEscalationStatusHistoryCommand(id,escalation.id(),escalationstatus.id(),next));
        assertEquals(first.id(),changed.id());
        assertEquals(first.createdAt(),changed.createdAt());
        assertEquals(next,changed.changedAt());
        assertEquals(changed,new GetChatEscalationStatusHistoryByIdUseCase(repo).execute(id));
        assertEquals(2,new ListChatEscalationStatusHistoryUseCase(repo).execute().size());
        assertEquals(id,new DeleteChatEscalationStatusHistoryUseCase(repo).execute(id).id());
        assertThrows(ChatEscalationStatusHistoryNotFoundApplicationException.class,()->new GetChatEscalationStatusHistoryByIdUseCase(repo).execute(id));
        assertThrows(ChatEscalationStatusHistoryNotFoundApplicationException.class,()->new DeleteChatEscalationStatusHistoryUseCase(repo).execute(id));
        assertThrows(ChatEscalationStatusHistoryNotFoundApplicationException.class,()->update(repo).execute(new UpdateChatEscalationStatusHistoryCommand(id,escalation.id(),escalationstatus.id(),date)));
    }
    @Test void missingParentsPreventRegistration() {
        var repo=new MemoryRepository();
        assertThrows(ChatEscalationNotFoundApplicationException.class,()->register(repo).execute(new RegisterChatEscalationStatusHistoryCommand(ChatEscalationId.generate(),escalationstatus.id(),date)));
        assertThrows(EscalationStatusNotFoundApplicationException.class,()->register(repo).execute(new RegisterChatEscalationStatusHistoryCommand(escalation.id(),EscalationStatusId.generate(),date)));
        assertEquals(0,repo.saves);
    }
    @Test void missingParentsPreventUpdateWithoutMutation() {
        var repo=new MemoryRepository();
        var first=register(repo).execute(new RegisterChatEscalationStatusHistoryCommand(escalation.id(),escalationstatus.id(),date));
        var id=new ChatEscalationStatusHistoryId(first.id());
        assertThrows(ChatEscalationNotFoundApplicationException.class,()->update(repo).execute(new UpdateChatEscalationStatusHistoryCommand(id,ChatEscalationId.generate(),escalationstatus.id(),date.plusDays(1))));
        assertThrows(EscalationStatusNotFoundApplicationException.class,()->update(repo).execute(new UpdateChatEscalationStatusHistoryCommand(id,escalation.id(),EscalationStatusId.generate(),date.plusDays(1))));
        assertEquals(first,new GetChatEscalationStatusHistoryByIdUseCase(repo).execute(id));
        assertEquals(1,repo.saves);
    }
    private RegisterChatEscalationStatusHistoryUseCase register(MemoryRepository repo) { return new RegisterChatEscalationStatusHistoryUseCase(repo,escalations,statuses); }
    private UpdateChatEscalationStatusHistoryUseCase update(MemoryRepository repo) { return new UpdateChatEscalationStatusHistoryUseCase(repo,escalations,statuses); }
    private static <T> T lookup(Class<T> type,Map<?,?> values) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(),new Class<?>[]{type},(proxy,method,args)-> {
            if(method.getName().equals("findById")) return Optional.ofNullable(values.get(args[0]));
            throw new UnsupportedOperationException(method.getName());
        }));
    }
    private static class MemoryRepository implements ChatEscalationStatusHistoryRepository {
        private final Map<ChatEscalationStatusHistoryId,ChatEscalationStatusHistory> values=new LinkedHashMap<>();
        private int saves;
        public ChatEscalationStatusHistory save(ChatEscalationStatusHistory value) { saves++; values.put(value.id(),value); return value; }
        public Optional<ChatEscalationStatusHistory> findById(ChatEscalationStatusHistoryId id) { return Optional.ofNullable(values.get(id)); }
        public List<ChatEscalationStatusHistory> findAll() { return List.copyOf(values.values()); }
        public void delete(ChatEscalationStatusHistory value) { values.remove(value.id()); }
    }
}
