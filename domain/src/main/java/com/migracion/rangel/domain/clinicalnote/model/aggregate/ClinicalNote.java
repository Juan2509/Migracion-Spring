package com.migracion.rangel.domain.clinicalnote.model.aggregate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.clinicalnote.model.valueobject.ClinicalNoteId;
import com.migracion.rangel.domain.clinicalnote.event.ClinicalNoteRegisteredEvent;
import com.migracion.rangel.domain.clinicalnote.event.ClinicalNoteUpdatedEvent;
public final class ClinicalNote extends AggregateRoot {
    private final ClinicalNoteId id;
    private EncounterId encounterId;
    private String subjective;
    private String objective;
    private String assessment;
    private String plan;
    private String additionalNotes;
    private OffsetDateTime signedAt;
    private ProfessionalId professionalId;
    private final OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private ClinicalNote(ClinicalNoteId id, EncounterId encounterId, String subjective, String objective, String assessment, String plan, String additionalNotes, OffsetDateTime signedAt, ProfessionalId professionalId, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(encounterId, subjective, objective, assessment, plan, additionalNotes, signedAt, professionalId);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }
    public static ClinicalNote register(EncounterId encounterId, String subjective, String objective, String assessment, String plan, String additionalNotes, OffsetDateTime signedAt, ProfessionalId professionalId) {
        var now = OffsetDateTime.now(ZoneOffset.UTC);
        var aggregate = new ClinicalNote(ClinicalNoteId.generate(), encounterId, subjective, objective, assessment, plan, additionalNotes, signedAt, professionalId, now, now);
        aggregate.recordEvent(new ClinicalNoteRegisteredEvent(aggregate.id, now.toLocalDateTime()));
        return aggregate;
    }
    public static ClinicalNote restore(ClinicalNoteId id, EncounterId encounterId, String subjective, String objective, String assessment, String plan, String additionalNotes, OffsetDateTime signedAt, ProfessionalId professionalId, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        return new ClinicalNote(id, encounterId, subjective, objective, assessment, plan, additionalNotes, signedAt, professionalId, createdAt, updatedAt);
    }
    public void update(EncounterId encounterId, String subjective, String objective, String assessment, String plan, String additionalNotes, OffsetDateTime signedAt, ProfessionalId professionalId) {
        setDetails(encounterId, subjective, objective, assessment, plan, additionalNotes, signedAt, professionalId);
        var now = OffsetDateTime.now(ZoneOffset.UTC);
        updatedAt = now;
        recordEvent(new ClinicalNoteUpdatedEvent(id, now.toLocalDateTime()));
    }
    private void setDetails(EncounterId encounterId, String subjective, String objective, String assessment, String plan, String additionalNotes, OffsetDateTime signedAt, ProfessionalId professionalId) {
        // Validar todos los valores antes de modificar el agregado.
        Objects.requireNonNull(encounterId, "encounterId es obligatorio");
        Objects.requireNonNull(subjective, "subjective es obligatorio");
        Objects.requireNonNull(objective, "objective es obligatorio");
        Objects.requireNonNull(assessment, "assessment es obligatorio");
        Objects.requireNonNull(plan, "plan es obligatorio");
        Objects.requireNonNull(additionalNotes, "additionalNotes es obligatorio");
        Objects.requireNonNull(signedAt, "signedAt es obligatorio");
        Objects.requireNonNull(professionalId, "professionalId es obligatorio");
        this.encounterId = encounterId;
        this.subjective = subjective;
        this.objective = objective;
        this.assessment = assessment;
        this.plan = plan;
        this.additionalNotes = additionalNotes;
        this.signedAt = signedAt;
        this.professionalId = professionalId;
    }
    public ClinicalNoteId id() { return id; }
    public EncounterId encounterId() { return encounterId; }
    public String subjective() { return subjective; }
    public String objective() { return objective; }
    public String assessment() { return assessment; }
    public String plan() { return plan; }
    public String additionalNotes() { return additionalNotes; }
    public OffsetDateTime signedAt() { return signedAt; }
    public ProfessionalId professionalId() { return professionalId; }
    public OffsetDateTime createdAt() { return createdAt; }
    public OffsetDateTime updatedAt() { return updatedAt; }
}

