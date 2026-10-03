package com.migracion.rangel.domain.documenttype.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
public record DocumentTypeDeletedEvent(DocumentTypeId id, LocalDateTime occurredOn) implements DomainEvent {
    public DocumentTypeDeletedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}
