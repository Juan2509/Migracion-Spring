package com.migracion.rangel.domain.common.event;

import java.time.LocalDateTime;

/** Hecho ocurrido en el dominio, independiente de su mecanismo de publicación. */
public interface DomainEvent {

    LocalDateTime occurredOn();
}
