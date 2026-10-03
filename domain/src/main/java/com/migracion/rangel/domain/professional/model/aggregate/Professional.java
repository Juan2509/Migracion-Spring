package com.migracion.rangel.domain.professional.model.aggregate;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.professional.event.ProfessionalRegisteredEvent;
import com.migracion.rangel.domain.professional.event.ProfessionalUpdatedEvent;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;

public final class Professional extends AggregateRoot {
    private final ProfessionalId id;
    private DocumentTypeId documentTypeId;
    private String documentNumber;
    private String firstName;
    private String lastName;
    private ProfessionalTypeId professionalType;
    private String licenseNumber;
    private Boolean active;
    private CityMunicipalityId cityId;
    private final OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    private Professional(ProfessionalId id, DocumentTypeId documentTypeId, String documentNumber, String firstName, String lastName, ProfessionalTypeId professionalType, String licenseNumber, Boolean active, CityMunicipalityId cityId,
            OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(documentTypeId, documentNumber, firstName, lastName, professionalType, licenseNumber, active, cityId);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }
    public static Professional register(DocumentTypeId documentTypeId, String documentNumber, String firstName, String lastName, ProfessionalTypeId professionalType, String licenseNumber, Boolean active, CityMunicipalityId cityId) {
        var now = OffsetDateTime.now(ZoneOffset.UTC);
        var professional = new Professional(ProfessionalId.generate(), documentTypeId, documentNumber, firstName, lastName, professionalType, licenseNumber, active, cityId, now, now);
        professional.recordEvent(new ProfessionalRegisteredEvent(professional.id, now.toLocalDateTime()));
        return professional;
    }
    public static Professional restore(ProfessionalId id, DocumentTypeId documentTypeId, String documentNumber, String firstName, String lastName, ProfessionalTypeId professionalType, String licenseNumber, Boolean active, CityMunicipalityId cityId,
            OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        return new Professional(id, documentTypeId, documentNumber, firstName, lastName, professionalType, licenseNumber, active, cityId, createdAt, updatedAt);
    }
    public void update(DocumentTypeId documentTypeId, String documentNumber, String firstName, String lastName, ProfessionalTypeId professionalType, String licenseNumber, Boolean active, CityMunicipalityId cityId) {
        setDetails(documentTypeId, documentNumber, firstName, lastName, professionalType, licenseNumber, active, cityId);
        updatedAt = OffsetDateTime.now(ZoneOffset.UTC);
        recordEvent(new ProfessionalUpdatedEvent(id, updatedAt.toLocalDateTime()));
    }
    private void setDetails(DocumentTypeId documentTypeId, String documentNumber, String firstName, String lastName, ProfessionalTypeId professionalType, String licenseNumber, Boolean active, CityMunicipalityId cityId) {
        // Validar el conjunto antes de modificar el agregado.
        Objects.requireNonNull(documentTypeId, "documentTypeId es obligatorio");
        validateText(documentNumber, 30, "documentNumber");
        validateText(firstName, 60, "firstName");
        validateText(lastName, 60, "lastName");
        Objects.requireNonNull(professionalType, "professionalType es obligatorio");
        validateText(licenseNumber, 100, "licenseNumber");
        Objects.requireNonNull(active, "active es obligatorio");
        Objects.requireNonNull(cityId, "cityId es obligatorio");
        this.documentTypeId = documentTypeId;
        this.documentNumber = documentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.professionalType = professionalType;
        this.licenseNumber = licenseNumber;
        this.active = active;
        this.cityId = cityId;
    }
    private static void validateText(String value, int limit, String field) {
        Objects.requireNonNull(value, field + " es obligatorio");
        if (value.codePointCount(0, value.length()) > limit) {
            throw new IllegalArgumentException(field + " supera " + limit + " caracteres");
        }
    }
    public ProfessionalId id() { return id; }
    public DocumentTypeId documentTypeId() { return documentTypeId; }
    public String documentNumber() { return documentNumber; }
    public String firstName() { return firstName; }
    public String lastName() { return lastName; }
    public ProfessionalTypeId professionalType() { return professionalType; }
    public String licenseNumber() { return licenseNumber; }
    public Boolean active() { return active; }
    public CityMunicipalityId cityId() { return cityId; }
    public OffsetDateTime createdAt() { return createdAt; }
    public OffsetDateTime updatedAt() { return updatedAt; }
}
