package com.migracion.rangel.infrastructure.chatescalationassignment.adapters.out.persistence.mappers;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.chatescalationassignment.model.aggregate.ChatEscalationAssignment;
import com.migracion.rangel.domain.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;
import com.migracion.rangel.infrastructure.chatescalationassignment.adapters.out.persistence.entity.ChatEscalationAssignmentJpaEntity;
public class ChatEscalationAssignmentPersistenceMapper {
    public ChatEscalationAssignmentJpaEntity toJpa(ChatEscalationAssignment value) {
        if (value == null) { return null; }
        var entity = new ChatEscalationAssignmentJpaEntity();
        entity.setId(value.id().value());
        entity.setEscalationId(value.escalationId().value());
        entity.setProfessionalId(value.professionalId().value());
        entity.setAssignedAt(value.assignedAt());
        return entity;
    }
    public ChatEscalationAssignment toDomain(ChatEscalationAssignmentJpaEntity entity) {
        if (entity == null) { return null; }
        return ChatEscalationAssignment.restore(new ChatEscalationAssignmentId(entity.getId()), new ChatEscalationId(entity.getEscalationId()), new ProfessionalId(entity.getProfessionalId()), entity.getAssignedAt());
    }
}
