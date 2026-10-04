package com.migracion.rangel.domain.providermodelai.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.providermodelai.model.valueobject.ProviderModelAiId;
import com.migracion.rangel.domain.providermodelai.event.ProviderModelAiRegisteredEvent;
import com.migracion.rangel.domain.providermodelai.event.ProviderModelAiUpdatedEvent;

public final class ProviderModelAi extends AggregateRoot {
    private final ProviderModelAiId id;
    private String nameProviderAi;
    private String razonSocial;
    private Boolean isActive;
    private String sitioWeb;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private ProviderModelAi(ProviderModelAiId id, String nameProviderAi, String razonSocial, Boolean isActive, String sitioWeb, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(nameProviderAi, razonSocial, isActive, sitioWeb);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }
    public static ProviderModelAi register(String nameProviderAi, String razonSocial, Boolean isActive, String sitioWeb) {
        var now = LocalDateTime.now();
        var aggregate = new ProviderModelAi(ProviderModelAiId.generate(), nameProviderAi, razonSocial, isActive, sitioWeb, now, now);
        aggregate.recordEvent(new ProviderModelAiRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static ProviderModelAi restore(ProviderModelAiId id, String nameProviderAi, String razonSocial, Boolean isActive, String sitioWeb, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new ProviderModelAi(id, nameProviderAi, razonSocial, isActive, sitioWeb, createdAt, updatedAt);
    }
    public void update(String nameProviderAi, String razonSocial, Boolean isActive, String sitioWeb) {
        setDetails(nameProviderAi, razonSocial, isActive, sitioWeb);
        updatedAt = LocalDateTime.now();
        recordEvent(new ProviderModelAiUpdatedEvent(id, updatedAt));
    }
    private void setDetails(String nameProviderAi, String razonSocial, Boolean isActive, String sitioWeb) {
        validateText(nameProviderAi, 100, "nameProviderAi");
        Objects.requireNonNull(razonSocial, "razonSocial es obligatorio");
        Objects.requireNonNull(isActive, "isActive es obligatorio");
        Objects.requireNonNull(sitioWeb, "sitioWeb es obligatorio");
        this.sitioWeb = sitioWeb;
        this.nameProviderAi = nameProviderAi;
        this.razonSocial = razonSocial;
        this.isActive = isActive;
    }
    private static void validateText(String value, int limit, String field) {
        Objects.requireNonNull(value, field + " es obligatorio");
        if (value.codePointCount(0, value.length()) > limit) {
            throw new IllegalArgumentException(field + " supera " + limit + " caracteres");
        }
    }
    public ProviderModelAiId id() { return id; }
    public String nameProviderAi() { return nameProviderAi; }
    public String razonSocial() { return razonSocial; }
    public String sitioWeb() { return sitioWeb; }
    public Boolean isActive() { return isActive; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}

