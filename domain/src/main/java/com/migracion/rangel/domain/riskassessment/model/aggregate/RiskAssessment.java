package com.migracion.rangel.domain.riskassessment.model.aggregate;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.risklevel.model.valueobject.RiskLevelId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.riskassessment.model.valueobject.RiskAssessmentId;
import com.migracion.rangel.domain.riskassessment.event.RiskAssessmentRegisteredEvent;
import com.migracion.rangel.domain.riskassessment.event.RiskAssessmentUpdatedEvent;
public final class RiskAssessment extends AggregateRoot {
    private final RiskAssessmentId id;
    private EncounterId encounterId;
    private RiskLevelId riskLevelId;
    private Boolean suicidalIdeation;
    private Boolean suicidePlan;
    private Boolean suicideIntent;
    private Boolean selfHarm;
    private Boolean harmToOthers;
    private String riskFactors;
    private String protectiveFactors;
    private String clinicalActions;
    private String observations;
    private OffsetDateTime assessedAt;
    private ProfessionalId assessedBy;
    private RiskAssessment(RiskAssessmentId id, EncounterId encounterId, RiskLevelId riskLevelId, Boolean suicidalIdeation, Boolean suicidePlan, Boolean suicideIntent, Boolean selfHarm, Boolean harmToOthers, String riskFactors, String protectiveFactors, String clinicalActions, String observations, OffsetDateTime assessedAt, ProfessionalId assessedBy) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(encounterId, riskLevelId, suicidalIdeation, suicidePlan, suicideIntent, selfHarm, harmToOthers, riskFactors, protectiveFactors, clinicalActions, observations, assessedAt, assessedBy);
    }
    public static RiskAssessment register(EncounterId encounterId, RiskLevelId riskLevelId, Boolean suicidalIdeation, Boolean suicidePlan, Boolean suicideIntent, Boolean selfHarm, Boolean harmToOthers, String riskFactors, String protectiveFactors, String clinicalActions, String observations, OffsetDateTime assessedAt, ProfessionalId assessedBy) {
        var aggregate = new RiskAssessment(RiskAssessmentId.generate(), encounterId, riskLevelId, suicidalIdeation, suicidePlan, suicideIntent, selfHarm, harmToOthers, riskFactors, protectiveFactors, clinicalActions, observations, assessedAt, assessedBy);
        aggregate.recordEvent(new RiskAssessmentRegisteredEvent(aggregate.id, LocalDateTime.now()));
        return aggregate;
    }
    public static RiskAssessment restore(RiskAssessmentId id, EncounterId encounterId, RiskLevelId riskLevelId, Boolean suicidalIdeation, Boolean suicidePlan, Boolean suicideIntent, Boolean selfHarm, Boolean harmToOthers, String riskFactors, String protectiveFactors, String clinicalActions, String observations, OffsetDateTime assessedAt, ProfessionalId assessedBy) {
        return new RiskAssessment(id, encounterId, riskLevelId, suicidalIdeation, suicidePlan, suicideIntent, selfHarm, harmToOthers, riskFactors, protectiveFactors, clinicalActions, observations, assessedAt, assessedBy);
    }
    public void update(EncounterId encounterId, RiskLevelId riskLevelId, Boolean suicidalIdeation, Boolean suicidePlan, Boolean suicideIntent, Boolean selfHarm, Boolean harmToOthers, String riskFactors, String protectiveFactors, String clinicalActions, String observations, OffsetDateTime assessedAt, ProfessionalId assessedBy) {
        setDetails(encounterId, riskLevelId, suicidalIdeation, suicidePlan, suicideIntent, selfHarm, harmToOthers, riskFactors, protectiveFactors, clinicalActions, observations, assessedAt, assessedBy);
        recordEvent(new RiskAssessmentUpdatedEvent(id, LocalDateTime.now()));
    }
    private void setDetails(EncounterId encounterId, RiskLevelId riskLevelId, Boolean suicidalIdeation, Boolean suicidePlan, Boolean suicideIntent, Boolean selfHarm, Boolean harmToOthers, String riskFactors, String protectiveFactors, String clinicalActions, String observations, OffsetDateTime assessedAt, ProfessionalId assessedBy) {
        // Validar todos los campos antes de modificar el agregado.
        Objects.requireNonNull(encounterId, "encounterId es obligatorio");
        Objects.requireNonNull(riskLevelId, "riskLevelId es obligatorio");
        Objects.requireNonNull(suicidalIdeation, "suicidalIdeation es obligatorio");
        Objects.requireNonNull(suicidePlan, "suicidePlan es obligatorio");
        Objects.requireNonNull(suicideIntent, "suicideIntent es obligatorio");
        Objects.requireNonNull(selfHarm, "selfHarm es obligatorio");
        Objects.requireNonNull(harmToOthers, "harmToOthers es obligatorio");
        Objects.requireNonNull(riskFactors, "riskFactors es obligatorio");
        Objects.requireNonNull(protectiveFactors, "protectiveFactors es obligatorio");
        Objects.requireNonNull(clinicalActions, "clinicalActions es obligatorio");
        Objects.requireNonNull(observations, "observations es obligatorio");
        Objects.requireNonNull(assessedAt, "assessedAt es obligatorio");
        Objects.requireNonNull(assessedBy, "assessedBy es obligatorio");
        this.encounterId = encounterId;
        this.riskLevelId = riskLevelId;
        this.suicidalIdeation = suicidalIdeation;
        this.suicidePlan = suicidePlan;
        this.suicideIntent = suicideIntent;
        this.selfHarm = selfHarm;
        this.harmToOthers = harmToOthers;
        this.riskFactors = riskFactors;
        this.protectiveFactors = protectiveFactors;
        this.clinicalActions = clinicalActions;
        this.observations = observations;
        this.assessedAt = assessedAt;
        this.assessedBy = assessedBy;
    }
    public RiskAssessmentId id() { return id; }
    public EncounterId encounterId() { return encounterId; }
    public RiskLevelId riskLevelId() { return riskLevelId; }
    public Boolean suicidalIdeation() { return suicidalIdeation; }
    public Boolean suicidePlan() { return suicidePlan; }
    public Boolean suicideIntent() { return suicideIntent; }
    public Boolean selfHarm() { return selfHarm; }
    public Boolean harmToOthers() { return harmToOthers; }
    public String riskFactors() { return riskFactors; }
    public String protectiveFactors() { return protectiveFactors; }
    public String clinicalActions() { return clinicalActions; }
    public String observations() { return observations; }
    public OffsetDateTime assessedAt() { return assessedAt; }
    public ProfessionalId assessedBy() { return assessedBy; }
}
