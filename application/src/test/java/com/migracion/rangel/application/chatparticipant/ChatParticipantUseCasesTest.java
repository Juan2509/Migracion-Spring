package com.migracion.rangel.application.chatparticipant;
import java.lang.reflect.Proxy;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.chatparticipant.command.*;
import com.migracion.rangel.application.chatparticipant.usecase.*;
import com.migracion.rangel.application.chatparticipant.exception.ChatParticipantNotFoundApplicationException;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import com.migracion.rangel.application.sendertype.exception.SenderTypeNotFoundApplicationException;
import com.migracion.rangel.application.patient.exception.PatientNotFoundApplicationException;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
import com.migracion.rangel.domain.chatparticipant.model.aggregate.ChatParticipant;
import com.migracion.rangel.domain.chatparticipant.model.valueobject.ChatParticipantId;
import com.migracion.rangel.domain.chatparticipant.port.repository.ChatParticipantRepository;
import com.migracion.rangel.domain.chatconversation.model.aggregate.ChatConversation;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.domain.conversationstatus.model.valueobject.ConversationStatusId;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
import com.migracion.rangel.domain.sendertype.model.aggregate.SenderType;
import com.migracion.rangel.domain.sendertype.model.valueobject.SenderTypeId;
import com.migracion.rangel.domain.sendertype.port.repository.SenderTypeRepository;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.patient.port.repository.PatientRepository;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
class ChatParticipantUseCasesTest {
    private final ChatConversation conversation = ChatConversation.register(ConversationStatusId.generate(), PriorityId.generate(), null, null, null, null);
    private final SenderType type = SenderType.register("Paciente");
    private final ChatConversationRepository conversations = lookup(ChatConversationRepository.class, Map.of(conversation.id(),conversation));
    private final SenderTypeRepository types = lookup(SenderTypeRepository.class, Map.of(type.id(),type));
    private final PatientRepository patients = lookup(PatientRepository.class, Map.of());
    private final ProfessionalRepository professionals = lookup(ProfessionalRepository.class, Map.of());
    @Test
    void crudAllowsRepeatedAssociationsAndNullOptionalReferences() {
        var repository = new MemoryRepository();
        var register = register(repository);
        var command = new RegisterChatParticipantCommand(conversation.id(), type.id(), null, null);
        var first = register.execute(command);
        var second = register.execute(command);
        assertNotEquals(first.id(), second.id());
        var id = new ChatParticipantId(first.id());
        var changed = update(repository).execute(new UpdateChatParticipantCommand(id, conversation.id(), type.id(), null, null));
        assertEquals(first.createdAt(), changed.createdAt());
        assertNull(changed.patientId());
        assertNull(changed.professionalId());
        assertEquals(changed, new GetChatParticipantByIdUseCase(repository).execute(id));
        assertEquals(2, new ListChatParticipantUseCase(repository).execute().size());
        assertEquals(id, new DeleteChatParticipantUseCase(repository).execute(id).id());
        assertThrows(ChatParticipantNotFoundApplicationException.class, () -> new GetChatParticipantByIdUseCase(repository).execute(id));
        assertThrows(ChatParticipantNotFoundApplicationException.class, () -> new DeleteChatParticipantUseCase(repository).execute(id));
        assertThrows(ChatParticipantNotFoundApplicationException.class, () -> update(repository).execute(new UpdateChatParticipantCommand(id, conversation.id(), type.id(), null, null)));
    }
    @Test
    void allMissingReferencesPreventRegistration() {
        var repository = new MemoryRepository();
        var register = register(repository);
        assertThrows(ChatConversationNotFoundApplicationException.class, () -> register.execute(new RegisterChatParticipantCommand(ChatConversationId.generate(), type.id(), null, null)));
        assertThrows(SenderTypeNotFoundApplicationException.class, () -> register.execute(new RegisterChatParticipantCommand(conversation.id(), SenderTypeId.generate(), null, null)));
        assertThrows(PatientNotFoundApplicationException.class, () -> register.execute(new RegisterChatParticipantCommand(conversation.id(), type.id(), PatientId.generate(), null)));
        assertThrows(ProfessionalNotFoundApplicationException.class, () -> register.execute(new RegisterChatParticipantCommand(conversation.id(), type.id(), null, ProfessionalId.generate())));
        assertEquals(0, repository.saves);
        assertTrue(repository.findAll().isEmpty());
    }
    @Test
    void allMissingReferencesPreventUpdateWithoutChangingRecord() {
        var repository = new MemoryRepository();
        var first = register(repository).execute(new RegisterChatParticipantCommand(conversation.id(), type.id(), null, null));
        var id = new ChatParticipantId(first.id());
        var update = update(repository);
        assertThrows(ChatConversationNotFoundApplicationException.class, () -> update.execute(new UpdateChatParticipantCommand(id, ChatConversationId.generate(), type.id(), null, null)));
        assertThrows(SenderTypeNotFoundApplicationException.class, () -> update.execute(new UpdateChatParticipantCommand(id, conversation.id(), SenderTypeId.generate(), null, null)));
        assertThrows(PatientNotFoundApplicationException.class, () -> update.execute(new UpdateChatParticipantCommand(id, conversation.id(), type.id(), PatientId.generate(), null)));
        assertThrows(ProfessionalNotFoundApplicationException.class, () -> update.execute(new UpdateChatParticipantCommand(id, conversation.id(), type.id(), null, ProfessionalId.generate())));
        assertEquals(first, new GetChatParticipantByIdUseCase(repository).execute(id));
        assertEquals(1, repository.saves);
    }
    private RegisterChatParticipantUseCase register(MemoryRepository repository) {
        return new RegisterChatParticipantUseCase(repository, conversations, types, patients, professionals);
    }
    private UpdateChatParticipantUseCase update(MemoryRepository repository) {
        return new UpdateChatParticipantUseCase(repository, conversations, types, patients, professionals);
    }
    private static <T> T lookup(Class<T> type, Map<?,?> values) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, (proxy, method, args) -> {
            if (method.getName().equals("findById")) { return Optional.ofNullable(values.get(args[0])); }
            throw new UnsupportedOperationException(method.getName());
        }));
    }
    private static class MemoryRepository implements ChatParticipantRepository {
        private final Map<ChatParticipantId,ChatParticipant> values = new LinkedHashMap<>();
        private int saves;
        public ChatParticipant save(ChatParticipant value) { saves++; values.put(value.id(),value); return value; }
        public Optional<ChatParticipant> findById(ChatParticipantId id) { return Optional.ofNullable(values.get(id)); }
        public List<ChatParticipant> findAll() { return List.copyOf(values.values()); }
        public void delete(ChatParticipant value) { values.remove(value.id()); }
    }
}
