package com.migracion.rangel.domain.relationshiptype.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.relationshiptype.model.valueobject.RelationshipTypeId;
public record RelationshipTypeUpdatedEvent(RelationshipTypeId id, LocalDateTime occurredOn) implements DomainEvent {
    public RelationshipTypeUpdatedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}
