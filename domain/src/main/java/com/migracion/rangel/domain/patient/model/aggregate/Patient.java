package com.migracion.rangel.domain.patient.model.aggregate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;

import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.patient.event.PatientRegisteredEvent;
import com.migracion.rangel.domain.patient.event.PatientUpdatedEvent;

public final class Patient extends AggregateRoot {
    private final PatientId id;
    private DocumentTypeId documentTypeId;
    private String documentNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private String secondLastName;
    private LocalDate birthDate;
    private GenderId biologicalSexId;
    private GenderId genderIdentity;
    private String email;
    private String phone;
    private String address;
    private Boolean active;
    private CityMunicipalityId cityId;
    private final LocalDateTime createdAt;
    private final ProfessionalId createdBy;
    private LocalDateTime updatedAt;
    private ProfessionalId updatedBy;

    private Patient(PatientId id, DocumentTypeId documentTypeId, String documentNumber, String firstName, String middleName, String lastName, String secondLastName, LocalDate birthDate, GenderId biologicalSexId, GenderId genderIdentity, String email, String phone, String address, Boolean active, CityMunicipalityId cityId, LocalDateTime createdAt,
            ProfessionalId createdBy, LocalDateTime updatedAt, ProfessionalId updatedBy) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.createdBy = createdBy;
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
        setDetails(documentTypeId, documentNumber, firstName, middleName, lastName, secondLastName, birthDate, biologicalSexId, genderIdentity, email, phone, address, active, cityId, updatedBy);
    }
    public static Patient register(DocumentTypeId documentTypeId, String documentNumber, String firstName, String middleName, String lastName, String secondLastName, LocalDate birthDate, GenderId biologicalSexId, GenderId genderIdentity, String email, String phone, String address, Boolean active, CityMunicipalityId cityId, ProfessionalId createdBy) {
        var now = LocalDateTime.now();
        var patient = new Patient(PatientId.generate(), documentTypeId, documentNumber, firstName, middleName, lastName, secondLastName, birthDate, biologicalSexId, genderIdentity, email, phone, address, active, cityId, now, createdBy, now, null);
        patient.recordEvent(new PatientRegisteredEvent(patient.id, now));
        return patient;
    }
    public static Patient restore(PatientId id, DocumentTypeId documentTypeId, String documentNumber, String firstName, String middleName, String lastName, String secondLastName, LocalDate birthDate, GenderId biologicalSexId, GenderId genderIdentity, String email, String phone, String address, Boolean active, CityMunicipalityId cityId, LocalDateTime createdAt,
            ProfessionalId createdBy, LocalDateTime updatedAt, ProfessionalId updatedBy) {
        return new Patient(id, documentTypeId, documentNumber, firstName, middleName, lastName, secondLastName, birthDate, biologicalSexId, genderIdentity, email, phone, address, active, cityId, createdAt, createdBy, updatedAt, updatedBy);
    }
    public void update(DocumentTypeId documentTypeId, String documentNumber, String firstName, String middleName, String lastName, String secondLastName, LocalDate birthDate, GenderId biologicalSexId, GenderId genderIdentity, String email, String phone, String address, Boolean active, CityMunicipalityId cityId, ProfessionalId updatedBy) {
        setDetails(documentTypeId, documentNumber, firstName, middleName, lastName, secondLastName, birthDate, biologicalSexId, genderIdentity, email, phone, address, active, cityId, updatedBy);
        updatedAt = LocalDateTime.now();
        recordEvent(new PatientUpdatedEvent(id, updatedAt));
    }
    private void setDetails(DocumentTypeId documentTypeId, String documentNumber, String firstName, String middleName, String lastName, String secondLastName, LocalDate birthDate, GenderId biologicalSexId, GenderId genderIdentity, String email, String phone, String address, Boolean active, CityMunicipalityId cityId, ProfessionalId updatedBy) {
        // Validar todo antes de modificar el estado.
        Objects.requireNonNull(documentTypeId, "documentTypeId es obligatorio");
        validateText(documentNumber, 30, "documentNumber");
        validateText(firstName, 50, "firstName");
        if (middleName != null) { validateText(middleName, 50, "middleName"); }
        validateText(lastName, 50, "lastName");
        if (secondLastName != null) { validateText(secondLastName, 50, "secondLastName"); }
        Objects.requireNonNull(birthDate, "birthDate es obligatorio");
        Objects.requireNonNull(biologicalSexId, "biologicalSexId es obligatorio");
        Objects.requireNonNull(genderIdentity, "genderIdentity es obligatorio");
        validateText(email, 150, "email");
        validateText(phone, 30, "phone");
        validateText(address, 250, "address");
        Objects.requireNonNull(active, "active es obligatorio");
        Objects.requireNonNull(cityId, "cityId es obligatorio");
        this.documentTypeId = documentTypeId;
        this.documentNumber = documentNumber;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.birthDate = birthDate;
        this.biologicalSexId = biologicalSexId;
        this.genderIdentity = genderIdentity;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.active = active;
        this.cityId = cityId;
        this.updatedBy = updatedBy;
    }
    private static void validateText(String value, int limit, String field) {
        Objects.requireNonNull(value, field + " es obligatorio");
        if (value.codePointCount(0, value.length()) > limit) {
            throw new IllegalArgumentException(field + " supera " + limit + " caracteres");
        }
    }
    public PatientId id() { return id; }
    public DocumentTypeId documentTypeId() { return documentTypeId; }
    public String documentNumber() { return documentNumber; }
    public String firstName() { return firstName; }
    public String middleName() { return middleName; }
    public String lastName() { return lastName; }
    public String secondLastName() { return secondLastName; }
    public LocalDate birthDate() { return birthDate; }
    public GenderId biologicalSexId() { return biologicalSexId; }
    public GenderId genderIdentity() { return genderIdentity; }
    public String email() { return email; }
    public String phone() { return phone; }
    public String address() { return address; }
    public Boolean active() { return active; }
    public CityMunicipalityId cityId() { return cityId; }
    public LocalDateTime createdAt() { return createdAt; }
    public ProfessionalId createdBy() { return createdBy; }
    public LocalDateTime updatedAt() { return updatedAt; }
    public ProfessionalId updatedBy() { return updatedBy; }
}

