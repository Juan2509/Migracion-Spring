package com.migracion.rangel.infrastructure.chatparticipant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.chatparticipant.model.aggregate.ChatParticipant;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.sendertype.model.valueobject.SenderTypeId;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.application.chatparticipant.dto.ChatParticipantResponse;
import com.migracion.rangel.infrastructure.chatparticipant.adapters.out.persistence.mappers.ChatParticipantPersistenceMapper;
class ChatParticipantPersistenceMapperTest {
    @Test
    void roundTripPreservesBothOptionalReferencesWithoutEvents() {
        roundTrip(ChatParticipant.register(ChatConversationId.generate(), SenderTypeId.generate(), PatientId.generate(), ProfessionalId.generate()));
    }
    @Test
    void roundTripPreservesNullReferencesWithoutEvents() {
        roundTrip(ChatParticipant.register(ChatConversationId.generate(), SenderTypeId.generate(), null, null));
    }
    private void roundTrip(ChatParticipant original) {
        var mapper = new ChatParticipantPersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(original));
        assertEquals(ChatParticipantResponse.from(original), ChatParticipantResponse.from(restored));
        assertTrue(restored.domainEvents().isEmpty());
    }
}
