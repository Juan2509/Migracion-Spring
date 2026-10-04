package com.migracion.rangel.domain.providermodelai.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.providermodelai.model.valueobject.ProviderModelAiId;
public record ProviderModelAiDeletedEvent(ProviderModelAiId id, LocalDateTime occurredOn) implements DomainEvent {
    public ProviderModelAiDeletedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

