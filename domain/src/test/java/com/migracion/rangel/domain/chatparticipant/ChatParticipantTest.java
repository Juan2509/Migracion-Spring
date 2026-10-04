package com.migracion.rangel.domain.chatparticipant;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.chatparticipant.model.aggregate.ChatParticipant;
import com.migracion.rangel.domain.chatparticipant.event.*;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.sendertype.model.valueobject.SenderTypeId;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
class ChatParticipantTest {
    @Test
    void optionalReferencesAndAuditFollowSchemaWithoutExclusiveRoleRule() {
        var conversation = ChatConversationId.generate();
        var type = SenderTypeId.generate();
        var original = ChatParticipant.register(conversation, type, null, null);
        assertInstanceOf(ChatParticipantRegisteredEvent.class, original.domainEvents().getFirst());
        var created = LocalDateTime.of(2020,1,1,0,0);
        var restored = ChatParticipant.restore(original.id(), conversation, type, null, null, created, created);
        assertTrue(restored.domainEvents().isEmpty());
        var patient = PatientId.generate();
        var professional = ProfessionalId.generate();
        restored.update(conversation, type, patient, professional);
        assertEquals(patient, restored.patientId());
        assertEquals(professional, restored.professionalId());
        assertEquals(original.id(), restored.id());
        assertEquals(created, restored.createdAt());
        assertTrue(restored.updatedAt().isAfter(created));
        assertInstanceOf(ChatParticipantUpdatedEvent.class, restored.domainEvents().getFirst());
        restored.update(conversation, type, null, null);
        assertNull(restored.patientId());
        assertNull(restored.professionalId());
    }
    @Test
    void invalidReferenceDoesNotPartiallyChangeAggregate() {
        var conversation = ChatConversationId.generate();
        var type = SenderTypeId.generate();
        var aggregate = ChatParticipant.register(conversation, type, null, null);
        var updatedAt = aggregate.updatedAt();
        assertThrows(NullPointerException.class,
                () -> aggregate.update(ChatConversationId.generate(), null, PatientId.generate(), ProfessionalId.generate()));
        assertEquals(conversation, aggregate.conversationId());
        assertEquals(type, aggregate.participantTypeId());
        assertNull(aggregate.patientId());
        assertEquals(updatedAt, aggregate.updatedAt());
        assertEquals(1, aggregate.domainEvents().size());
        assertThrows(NullPointerException.class, () -> ChatParticipant.register(null, type, null, null));
    }
}
