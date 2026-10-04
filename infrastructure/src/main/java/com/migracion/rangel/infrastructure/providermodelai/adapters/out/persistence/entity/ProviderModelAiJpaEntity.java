package com.migracion.rangel.infrastructure.providermodelai.adapters.out.persistence.entity;
import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.*;
@Entity
@Table(name = "provider_models_ai")
public class ProviderModelAiJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name_provider_ai", nullable = false, length = 100)
    private String nameProviderAi;

    @Column(name = "razon_social", nullable = false, columnDefinition = "VARCHAR")
    private String razonSocial;

    @Column(name = "\"isActive\"", nullable = false)
    private Boolean isActive;

    @Column(name = "sitio_web", nullable = false, columnDefinition = "TEXT")
    private String sitioWeb;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    public ProviderModelAiJpaEntity() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNameProviderAi() { return nameProviderAi; }
    public void setNameProviderAi(String nameProviderAi) { this.nameProviderAi = nameProviderAi; }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public String getSitioWeb() { return sitioWeb; }
    public void setSitioWeb(String sitioWeb) { this.sitioWeb = sitioWeb; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

