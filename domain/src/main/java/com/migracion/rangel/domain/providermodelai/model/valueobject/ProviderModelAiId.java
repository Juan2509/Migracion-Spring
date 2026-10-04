package com.migracion.rangel.domain.providermodelai.model.valueobject;
import java.util.Objects;
import java.util.UUID;
public record ProviderModelAiId(UUID value) {
    public ProviderModelAiId { Objects.requireNonNull(value, "El ID es obligatorio"); }
    public static ProviderModelAiId generate() { return new ProviderModelAiId(UUID.randomUUID()); }
}

