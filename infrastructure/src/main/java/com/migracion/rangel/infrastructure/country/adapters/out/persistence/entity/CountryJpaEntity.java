package com.migracion.rangel.infrastructure.country.adapters.out.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "countries")
public class CountryJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name_country", nullable = false, length = 50)
    private String nameCountry;

    @Column(name = "code_country", nullable = false, length = 10)
    private String codeCountry;

    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "telephone_prefix", nullable = false, length = 5)
    private String telephonePrefix;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public CountryJpaEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNameCountry() { return nameCountry; }
    public void setNameCountry(String nameCountry) { this.nameCountry = nameCountry; }

    public String getCodeCountry() { return codeCountry; }
    public void setCodeCountry(String codeCountry) { this.codeCountry = codeCountry; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public String getTelephonePrefix() { return telephonePrefix; }
    public void setTelephonePrefix(String telephonePrefix) { this.telephonePrefix = telephonePrefix; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
