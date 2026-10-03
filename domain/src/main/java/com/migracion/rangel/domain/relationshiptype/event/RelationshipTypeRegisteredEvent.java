package com.migracion.rangel.domain.relationshiptype.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.relationshiptype.model.valueobject.RelationshipTypeId;
public record RelationshipTypeRegisteredEvent(RelationshipTypeId id, LocalDateTime occurredOn) implements DomainEvent {
    public RelationshipTypeRegisteredEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}
