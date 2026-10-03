package com.migracion.rangel.infrastructure.patientcontact.adapters.out.persistence.entity;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "patient_contacts")
public class PatientContactJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "contact_id", nullable = false)
    private UUID contactId;

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "is_primary_contact", nullable = false)
    private Boolean isPrimaryContact;

    @Column(name = "is_emergency_contact", nullable = false)
    private Boolean isEmergencyContact;

    @Column(name = "relationship_type_id", nullable = false)
    private UUID relationshipTypeId;
    public PatientContactJpaEntity() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getContactId() { return contactId; }
    public void setContactId(UUID contactId) { this.contactId = contactId; }
    public UUID getPatientId() { return patientId; }
    public void setPatientId(UUID patientId) { this.patientId = patientId; }
    public Boolean getIsPrimaryContact() { return isPrimaryContact; }
    public void setIsPrimaryContact(Boolean isPrimaryContact) { this.isPrimaryContact = isPrimaryContact; }
    public Boolean getIsEmergencyContact() { return isEmergencyContact; }
    public void setIsEmergencyContact(Boolean isEmergencyContact) { this.isEmergencyContact = isEmergencyContact; }
    public UUID getRelationshipTypeId() { return relationshipTypeId; }
    public void setRelationshipTypeId(UUID relationshipTypeId) { this.relationshipTypeId = relationshipTypeId; }
}
