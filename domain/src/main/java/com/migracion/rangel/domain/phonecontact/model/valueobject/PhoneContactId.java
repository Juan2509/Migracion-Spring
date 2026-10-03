package com.migracion.rangel.domain.phonecontact.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record PhoneContactId(UUID value) {
    public PhoneContactId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static PhoneContactId generate() { return new PhoneContactId(UUID.randomUUID()); }
}
