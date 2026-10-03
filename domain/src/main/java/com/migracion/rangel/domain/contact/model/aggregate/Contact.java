package com.migracion.rangel.domain.contact.model.aggregate;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.domain.contact.event.ContactRegisteredEvent;
import com.migracion.rangel.domain.contact.event.ContactUpdatedEvent;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;

public final class Contact extends AggregateRoot {
    private final ContactId id;
    private String fullName;
    private String email;
    private String notes;
    private CityMunicipalityId cityId;
    private final OffsetDateTime createdAt;
    private final ProfessionalId createdBy;
    private OffsetDateTime updatedAt;
    private ProfessionalId updatedBy;

    private Contact(ContactId id, String fullName, String email, String notes,
            CityMunicipalityId cityId, OffsetDateTime createdAt, ProfessionalId createdBy,
            OffsetDateTime updatedAt, ProfessionalId updatedBy) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.createdBy = Objects.requireNonNull(createdBy, "createdBy es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
        setDetails(fullName, email, notes, cityId, updatedBy);
    }
    public static Contact register(String fullName, String email, String notes,
            CityMunicipalityId cityId, ProfessionalId createdBy) {
        var now = OffsetDateTime.now(ZoneOffset.UTC);
        var contact = new Contact(ContactId.generate(), fullName, email, notes,
                cityId, now, createdBy, now, null);
        contact.recordEvent(new ContactRegisteredEvent(contact.id, now.toLocalDateTime()));
        return contact;
    }
    public static Contact restore(ContactId id, String fullName, String email, String notes,
            CityMunicipalityId cityId, OffsetDateTime createdAt, ProfessionalId createdBy,
            OffsetDateTime updatedAt, ProfessionalId updatedBy) {
        return new Contact(id, fullName, email, notes, cityId, createdAt, createdBy, updatedAt, updatedBy);
    }
    public void update(String fullName, String email, String notes,
            CityMunicipalityId cityId, ProfessionalId updatedBy) {
        setDetails(fullName, email, notes, cityId, updatedBy);
        updatedAt = OffsetDateTime.now(ZoneOffset.UTC);
        recordEvent(new ContactUpdatedEvent(id, updatedAt.toLocalDateTime()));
    }
    private void setDetails(String fullName, String email, String notes,
            CityMunicipalityId cityId, ProfessionalId updatedBy) {
        validateText(fullName, 200, "fullName");
        validateText(email, 150, "email");
        Objects.requireNonNull(notes, "notes es obligatorio");
        Objects.requireNonNull(cityId, "cityId es obligatorio");
        this.fullName = fullName;
        this.email = email;
        this.notes = notes;
        this.cityId = cityId;
        this.updatedBy = updatedBy;
    }
    private static void validateText(String value, int limit, String field) {
        Objects.requireNonNull(value, field + " es obligatorio");
        if (value.codePointCount(0, value.length()) > limit) {
            throw new IllegalArgumentException(field + " supera " + limit + " caracteres");
        }
    }
    public ContactId id() { return id; }
    public String fullName() { return fullName; }
    public String email() { return email; }
    public String notes() { return notes; }
    public CityMunicipalityId cityId() { return cityId; }
    public OffsetDateTime createdAt() { return createdAt; }
    public ProfessionalId createdBy() { return createdBy; }
    public OffsetDateTime updatedAt() { return updatedAt; }
    public ProfessionalId updatedBy() { return updatedBy; }
}
