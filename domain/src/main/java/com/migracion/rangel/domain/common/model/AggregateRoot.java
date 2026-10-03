package com.migracion.rangel.domain.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.migracion.rangel.domain.common.event.DomainEvent;

/** Base de los agregados que conservan los eventos producidos por sus operaciones. */
public abstract class AggregateRoot {

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    protected final void recordEvent(DomainEvent event) {
        domainEvents.add(Objects.requireNonNull(event, "El evento no puede ser null"));
    }

    /** Devuelve una copia inmutable; consultar los eventos no los elimina. */
    public final List<DomainEvent> domainEvents() {
        return List.copyOf(domainEvents);
    }

    /** Limpia los eventos en memoria una vez que el consumidor los ha procesado. */
    public final void clearDomainEvents() {
        domainEvents.clear();
    }
}
