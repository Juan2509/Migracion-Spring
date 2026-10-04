package com.migracion.rangel.domain.providermodelai.event;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.event.DomainEvent;
import com.migracion.rangel.domain.providermodelai.model.valueobject.ProviderModelAiId;
public record ProviderModelAiUpdatedEvent(ProviderModelAiId id, LocalDateTime occurredOn) implements DomainEvent {
    public ProviderModelAiUpdatedEvent {
        Objects.requireNonNull(id, "El ID es obligatorio");
        Objects.requireNonNull(occurredOn, "La fecha es obligatoria");
    }
}

