package com.migracion.rangel.domain.patientallergy.model.aggregate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.patientallergy.model.valueobject.PatientAllergyId;
import com.migracion.rangel.domain.patientallergy.event.PatientAllergyRegisteredEvent;
import com.migracion.rangel.domain.patientallergy.event.PatientAllergyUpdatedEvent;
public final class PatientAllergy extends AggregateRoot {
    private final PatientAllergyId id;
    private PatientId patientId;
    private String substance;
    private String reaction;
    private String severity;
    private Boolean active;
    private OffsetDateTime recordedAt;
    private ProfessionalId recordedBy;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private PatientAllergy(PatientAllergyId id, PatientId patientId, String substance, String reaction, String severity, Boolean active, OffsetDateTime recordedAt, ProfessionalId recordedBy, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(patientId, substance, reaction, severity, active, recordedAt, recordedBy);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }
    public static PatientAllergy register(PatientId patientId, String substance, String reaction, String severity, Boolean active, OffsetDateTime recordedAt, ProfessionalId recordedBy) {
        var now = LocalDateTime.now();
        var aggregate = new PatientAllergy(PatientAllergyId.generate(), patientId, substance, reaction, severity, active, recordedAt, recordedBy, now, now);
        aggregate.recordEvent(new PatientAllergyRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static PatientAllergy restore(PatientAllergyId id, PatientId patientId, String substance, String reaction, String severity, Boolean active, OffsetDateTime recordedAt, ProfessionalId recordedBy, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new PatientAllergy(id, patientId, substance, reaction, severity, active, recordedAt, recordedBy, createdAt, updatedAt);
    }
    public void update(PatientId patientId, String substance, String reaction, String severity, Boolean active, OffsetDateTime recordedAt, ProfessionalId recordedBy) {
        setDetails(patientId, substance, reaction, severity, active, recordedAt, recordedBy);
        var now = LocalDateTime.now();
        updatedAt = now;
        recordEvent(new PatientAllergyUpdatedEvent(id, now));
    }
    private void setDetails(PatientId patientId, String substance, String reaction, String severity, Boolean active, OffsetDateTime recordedAt, ProfessionalId recordedBy) {
        // Validar todos los valores antes de modificar el agregado.
        Objects.requireNonNull(patientId, "patientId es obligatorio");
        validateText(substance, 200, "substance");
        validateText(severity, 20, "severity");
        Objects.requireNonNull(active, "active es obligatorio");
        Objects.requireNonNull(recordedAt, "recordedAt es obligatorio");
        Objects.requireNonNull(recordedBy, "recordedBy es obligatorio");
        this.patientId = patientId;
        this.substance = substance;
        this.reaction = reaction;
        this.severity = severity;
        this.active = active;
        this.recordedAt = recordedAt;
        this.recordedBy = recordedBy;
    }
    private static void validateText(String value, int limit, String field) {
        Objects.requireNonNull(value, field + " es obligatorio");
        if (value.codePointCount(0, value.length()) > limit) {
            throw new IllegalArgumentException(field + " supera " + limit + " caracteres");
        }
    }
    public PatientAllergyId id() { return id; }
    public PatientId patientId() { return patientId; }
    public String substance() { return substance; }
    public String reaction() { return reaction; }
    public String severity() { return severity; }
    public Boolean active() { return active; }
    public OffsetDateTime recordedAt() { return recordedAt; }
    public ProfessionalId recordedBy() { return recordedBy; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}
