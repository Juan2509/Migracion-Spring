package com.migracion.rangel.domain.contact.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record ContactId(UUID value) {
    public ContactId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static ContactId generate() { return new ContactId(UUID.randomUUID()); }
}
