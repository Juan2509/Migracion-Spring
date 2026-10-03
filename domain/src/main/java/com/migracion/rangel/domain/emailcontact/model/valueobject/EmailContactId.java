package com.migracion.rangel.domain.emailcontact.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record EmailContactId(UUID value) {
    public EmailContactId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static EmailContactId generate() { return new EmailContactId(UUID.randomUUID()); }
}
