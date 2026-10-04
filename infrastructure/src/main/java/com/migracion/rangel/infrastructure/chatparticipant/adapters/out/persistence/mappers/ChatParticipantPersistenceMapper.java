package com.migracion.rangel.infrastructure.chatparticipant.adapters.out.persistence.mappers;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.sendertype.model.valueobject.SenderTypeId;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.chatparticipant.model.aggregate.ChatParticipant;
import com.migracion.rangel.domain.chatparticipant.model.valueobject.ChatParticipantId;
import com.migracion.rangel.infrastructure.chatparticipant.adapters.out.persistence.entity.ChatParticipantJpaEntity;
public class ChatParticipantPersistenceMapper {
    public ChatParticipantJpaEntity toJpa(ChatParticipant aggregate) {
        if (aggregate == null) { return null; }
        var entity = new ChatParticipantJpaEntity();
        entity.setId(aggregate.id().value());
        entity.setConversationId(aggregate.conversationId().value());
        entity.setParticipantTypeId(aggregate.participantTypeId().value());
        entity.setPatientId(aggregate.patientId() == null ? null : aggregate.patientId().value());
        entity.setProfessionalId(aggregate.professionalId() == null ? null : aggregate.professionalId().value());
        entity.setCreatedAt(aggregate.createdAt());
        entity.setUpdatedAt(aggregate.updatedAt());
        return entity;
    }
    public ChatParticipant toDomain(ChatParticipantJpaEntity entity) {
        if (entity == null) { return null; }
        return ChatParticipant.restore(new ChatParticipantId(entity.getId()), new ChatConversationId(entity.getConversationId()), new SenderTypeId(entity.getParticipantTypeId()), entity.getPatientId() == null ? null : new PatientId(entity.getPatientId()), entity.getProfessionalId() == null ? null : new ProfessionalId(entity.getProfessionalId()), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
