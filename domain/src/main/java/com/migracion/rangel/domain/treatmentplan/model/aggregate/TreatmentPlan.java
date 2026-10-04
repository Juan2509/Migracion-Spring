package com.migracion.rangel.domain.treatmentplan.model.aggregate;
import java.time.OffsetDateTime;
import java.time.LocalDate;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
import java.time.ZoneOffset;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.treatmentplan.model.valueobject.TreatmentPlanId;
import com.migracion.rangel.domain.treatmentplan.event.TreatmentPlanRegisteredEvent;
import com.migracion.rangel.domain.treatmentplan.event.TreatmentPlanUpdatedEvent;
public final class TreatmentPlan extends AggregateRoot {
    private final TreatmentPlanId id;
    private EncounterId encounterId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private TreatmentStatusId treatmentStatusId;
    private ProfessionalId professionalId;
    private final OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private TreatmentPlan(TreatmentPlanId id, EncounterId encounterId, String title, String description, LocalDate startDate, LocalDate endDate, TreatmentStatusId treatmentStatusId, ProfessionalId professionalId, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(encounterId, title, description, startDate, endDate, treatmentStatusId, professionalId);
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt es obligatorio");
    }
    public static TreatmentPlan register(EncounterId encounterId, String title, String description, LocalDate startDate, LocalDate endDate, TreatmentStatusId treatmentStatusId, ProfessionalId professionalId) {
        var now = OffsetDateTime.now(ZoneOffset.UTC);
        var aggregate = new TreatmentPlan(TreatmentPlanId.generate(), encounterId, title, description, startDate, endDate, treatmentStatusId, professionalId, now, now);
        aggregate.recordEvent(new TreatmentPlanRegisteredEvent(aggregate.id, now.toLocalDateTime()));
        return aggregate;
    }
    public static TreatmentPlan restore(TreatmentPlanId id, EncounterId encounterId, String title, String description, LocalDate startDate, LocalDate endDate, TreatmentStatusId treatmentStatusId, ProfessionalId professionalId, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        return new TreatmentPlan(id, encounterId, title, description, startDate, endDate, treatmentStatusId, professionalId, createdAt, updatedAt);
    }
    public void update(EncounterId encounterId, String title, String description, LocalDate startDate, LocalDate endDate, TreatmentStatusId treatmentStatusId, ProfessionalId professionalId) {
        setDetails(encounterId, title, description, startDate, endDate, treatmentStatusId, professionalId);
        var now = OffsetDateTime.now(ZoneOffset.UTC);
        updatedAt = now;
        recordEvent(new TreatmentPlanUpdatedEvent(id, now.toLocalDateTime()));
    }
    private void setDetails(EncounterId encounterId, String title, String description, LocalDate startDate, LocalDate endDate, TreatmentStatusId treatmentStatusId, ProfessionalId professionalId) {
        // Validar todos los valores antes de modificar el agregado.
        Objects.requireNonNull(encounterId, "encounterId es obligatorio");
        Objects.requireNonNull(title, "title es obligatorio");
        if (title.codePointCount(0, title.length()) > 200) { throw new IllegalArgumentException("title supera 200 caracteres"); }
        Objects.requireNonNull(description, "description es obligatorio");
        Objects.requireNonNull(startDate, "startDate es obligatorio");
        Objects.requireNonNull(endDate, "endDate es obligatorio");
        Objects.requireNonNull(treatmentStatusId, "treatmentStatusId es obligatorio");
        Objects.requireNonNull(professionalId, "professionalId es obligatorio");
        this.encounterId = encounterId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.treatmentStatusId = treatmentStatusId;
        this.professionalId = professionalId;
    }
    public TreatmentPlanId id() { return id; }
    public EncounterId encounterId() { return encounterId; }
    public String title() { return title; }
    public String description() { return description; }
    public LocalDate startDate() { return startDate; }
    public LocalDate endDate() { return endDate; }
    public TreatmentStatusId treatmentStatusId() { return treatmentStatusId; }
    public ProfessionalId professionalId() { return professionalId; }
    public OffsetDateTime createdAt() { return createdAt; }
    public OffsetDateTime updatedAt() { return updatedAt; }
}


