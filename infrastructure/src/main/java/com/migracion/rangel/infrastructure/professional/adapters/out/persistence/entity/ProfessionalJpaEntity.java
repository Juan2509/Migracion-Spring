package com.migracion.rangel.infrastructure.professional.adapters.out.persistence.entity;
import java.time.OffsetDateTime;
import java.util.UUID;
import jakarta.persistence.*;
import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;

@Entity
@Table(name = "professionals", uniqueConstraints = {
        @UniqueConstraint(name = "uq_professionals_document_number", columnNames = "document_number"),
        @UniqueConstraint(name = "uq_professionals_first_name", columnNames = "first_name"),
        @UniqueConstraint(name = "uq_professionals_last_name", columnNames = "last_name"),
        @UniqueConstraint(name = "uq_professionals_license_number", columnNames = "license_number")
})
public class ProfessionalJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "document_type_id", nullable = false)
    private UUID documentTypeId;

    @Column(name = "document_number", nullable = false, length = 30)
    private String documentNumber;

    @Column(name = "first_name", nullable = false, length = 60)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 60)
    private String lastName;

    @Column(name = "professional_type", nullable = false)
    private UUID professionalType;

    @Column(name = "license_number", nullable = false, length = 100)
    private String licenseNumber;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "city_id", nullable = false)
    private UUID cityId;

    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
    public ProfessionalJpaEntity() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getDocumentTypeId() { return documentTypeId; }
    public void setDocumentTypeId(UUID documentTypeId) { this.documentTypeId = documentTypeId; }

    public String getDocumentNumber() { return documentNumber; }
    public void setDocumentNumber(String documentNumber) { this.documentNumber = documentNumber; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public UUID getProfessionalType() { return professionalType; }
    public void setProfessionalType(UUID professionalType) { this.professionalType = professionalType; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public UUID getCityId() { return cityId; }
    public void setCityId(UUID cityId) { this.cityId = cityId; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
