package com.migracion.rangel.infrastructure.phonecontact.adapters.out.persistence.entity;
import java.util.UUID;

import jakarta.persistence.*;
@Entity
@Table(name = "phone_contacts")
public class PhoneContactJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "contact_id", nullable = false)
    private UUID contactId;

    @Column(name = "phone", nullable = false, length = 30)
    private String phone;

    @Column(name = "notes", nullable = true, columnDefinition = "TEXT")
    private String notes;
    public PhoneContactJpaEntity() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getContactId() { return contactId; }
    public void setContactId(UUID contactId) { this.contactId = contactId; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
