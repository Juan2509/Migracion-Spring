package com.migracion.rangel.domain.diagnosticsystem.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.diagnosticsystem.model.valueobject.DiagnosticSystemId;
public record DiagnosticSystemUpdatedEvent(DiagnosticSystemId id, LocalDateTime occurredOn) implements DomainEvent {
    public DiagnosticSystemUpdatedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

