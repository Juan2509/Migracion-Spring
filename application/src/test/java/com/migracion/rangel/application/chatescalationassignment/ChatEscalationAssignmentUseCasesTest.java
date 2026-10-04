package com.migracion.rangel.application.chatescalationassignment;
import java.time.LocalDateTime;
import java.lang.reflect.Proxy;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.chatescalationassignment.command.*;
import com.migracion.rangel.application.chatescalationassignment.usecase.*;
import com.migracion.rangel.application.chatescalationassignment.exception.ChatEscalationAssignmentNotFoundApplicationException;
import com.migracion.rangel.application.chatescalation.exception.ChatEscalationNotFoundApplicationException;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
import com.migracion.rangel.domain.chatescalationassignment.model.aggregate.ChatEscalationAssignment;
import com.migracion.rangel.domain.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;
import com.migracion.rangel.domain.chatescalationassignment.port.repository.ChatEscalationAssignmentRepository;
import com.migracion.rangel.domain.chatescalation.model.aggregate.ChatEscalation;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import com.migracion.rangel.domain.chatescalation.port.repository.ChatEscalationRepository;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import com.migracion.rangel.domain.professional.model.aggregate.Professional;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
class ChatEscalationAssignmentUseCasesTest {
    private final ChatEscalation escalation=ChatEscalation.register(ChatConversationId.generate(),"Motivo",EscalationStatusId.generate(),false);
    private final Professional professional=Professional.register(DocumentTypeId.generate(),"123","Ana","Perez",ProfessionalTypeId.generate(),"L123",false,CityMunicipalityId.generate());
    private final ChatEscalationRepository escalations=lookup(ChatEscalationRepository.class,Map.of(escalation.id(),escalation));
    private final ProfessionalRepository professionals=lookup(ProfessionalRepository.class,Map.of(professional.id(),professional));
    private final LocalDateTime date=LocalDateTime.of(2021,2,3,4,5);
    @Test void crudAllowsRepeatedAssignmentsAndSuppliedDates() {
        var repo=new MemoryRepository();
        var command=new RegisterChatEscalationAssignmentCommand(escalation.id(),professional.id(),date);
        var first=register(repo).execute(command);
        var second=register(repo).execute(command);
        assertNotEquals(first.id(),second.id());
        assertEquals(date,first.assignedAt());
        var id=new ChatEscalationAssignmentId(first.id());
        var next=date.plusDays(3);
        var changed=update(repo).execute(new UpdateChatEscalationAssignmentCommand(id,escalation.id(),professional.id(),next));
        assertEquals(first.id(),changed.id());
        assertEquals(next,changed.assignedAt());
        assertEquals(changed,new GetChatEscalationAssignmentByIdUseCase(repo).execute(id));
        assertEquals(2,new ListChatEscalationAssignmentUseCase(repo).execute().size());
        assertEquals(id,new DeleteChatEscalationAssignmentUseCase(repo).execute(id).id());
        assertThrows(ChatEscalationAssignmentNotFoundApplicationException.class,()->new GetChatEscalationAssignmentByIdUseCase(repo).execute(id));
        assertThrows(ChatEscalationAssignmentNotFoundApplicationException.class,()->new DeleteChatEscalationAssignmentUseCase(repo).execute(id));
        assertThrows(ChatEscalationAssignmentNotFoundApplicationException.class,()->update(repo).execute(new UpdateChatEscalationAssignmentCommand(id,escalation.id(),professional.id(),date)));
    }
    @Test void missingParentsPreventRegistration() {
        var repo=new MemoryRepository();
        assertThrows(ChatEscalationNotFoundApplicationException.class,()->register(repo).execute(new RegisterChatEscalationAssignmentCommand(ChatEscalationId.generate(),professional.id(),date)));
        assertThrows(ProfessionalNotFoundApplicationException.class,()->register(repo).execute(new RegisterChatEscalationAssignmentCommand(escalation.id(),ProfessionalId.generate(),date)));
        assertEquals(0,repo.saves);
    }
    @Test void missingParentsPreventUpdateWithoutMutation() {
        var repo=new MemoryRepository();
        var first=register(repo).execute(new RegisterChatEscalationAssignmentCommand(escalation.id(),professional.id(),date));
        var id=new ChatEscalationAssignmentId(first.id());
        assertThrows(ChatEscalationNotFoundApplicationException.class,()->update(repo).execute(new UpdateChatEscalationAssignmentCommand(id,ChatEscalationId.generate(),professional.id(),date.plusDays(1))));
        assertThrows(ProfessionalNotFoundApplicationException.class,()->update(repo).execute(new UpdateChatEscalationAssignmentCommand(id,escalation.id(),ProfessionalId.generate(),date.plusDays(1))));
        assertEquals(first,new GetChatEscalationAssignmentByIdUseCase(repo).execute(id));
        assertEquals(1,repo.saves);
    }
    private RegisterChatEscalationAssignmentUseCase register(MemoryRepository repo) { return new RegisterChatEscalationAssignmentUseCase(repo,escalations,professionals); }
    private UpdateChatEscalationAssignmentUseCase update(MemoryRepository repo) { return new UpdateChatEscalationAssignmentUseCase(repo,escalations,professionals); }
    private static <T> T lookup(Class<T> type,Map<?,?> values) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(),new Class<?>[]{type},(proxy,method,args)-> {
            if(method.getName().equals("findById")) return Optional.ofNullable(values.get(args[0]));
            throw new UnsupportedOperationException(method.getName());
        }));
    }
    private static class MemoryRepository implements ChatEscalationAssignmentRepository {
        private final Map<ChatEscalationAssignmentId,ChatEscalationAssignment> values=new LinkedHashMap<>();
        private int saves;
        public ChatEscalationAssignment save(ChatEscalationAssignment value) { saves++; values.put(value.id(),value); return value; }
        public Optional<ChatEscalationAssignment> findById(ChatEscalationAssignmentId id) { return Optional.ofNullable(values.get(id)); }
        public List<ChatEscalationAssignment> findAll() { return List.copyOf(values.values()); }
        public void delete(ChatEscalationAssignment value) { values.remove(value.id()); }
    }
}
