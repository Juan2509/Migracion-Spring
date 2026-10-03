package com.migracion.rangel.domain.mentalstatusexam.model.aggregate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.mentalstatusexam.model.valueobject.MentalStatusExamId;
import com.migracion.rangel.domain.mentalstatusexam.event.MentalStatusExamRegisteredEvent;
import com.migracion.rangel.domain.mentalstatusexam.event.MentalStatusExamUpdatedEvent;
public final class MentalStatusExam extends AggregateRoot {
    private final MentalStatusExamId id;
    private EncounterId encounterId;
    private String appearance;
    private String behavior;
    private String attitude;
    private String consciousness;
    private String orientation;
    private String attention;
    private String memory;
    private String speech;
    private String mood;
    private String affect;
    private String thoughtProcess;
    private String thoughtContent;
    private String perception;
    private String judgment;
    private String insight;
    private String psychomotorActivity;
    private String observations;
    private final OffsetDateTime createdAt;
    private final ProfessionalId createdBy;
    private MentalStatusExam(MentalStatusExamId id, EncounterId encounterId, String appearance, String behavior, String attitude, String consciousness, String orientation, String attention, String memory, String speech, String mood, String affect, String thoughtProcess, String thoughtContent, String perception, String judgment, String insight, String psychomotorActivity, String observations, ProfessionalId createdBy, OffsetDateTime createdAt) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        this.createdBy = Objects.requireNonNull(createdBy, "createdBy es obligatorio");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt es obligatorio");
        setDetails(encounterId, appearance, behavior, attitude, consciousness, orientation, attention, memory, speech, mood, affect, thoughtProcess, thoughtContent, perception, judgment, insight, psychomotorActivity, observations);
    }
    public static MentalStatusExam register(EncounterId encounterId, String appearance, String behavior, String attitude, String consciousness, String orientation, String attention, String memory, String speech, String mood, String affect, String thoughtProcess, String thoughtContent, String perception, String judgment, String insight, String psychomotorActivity, String observations, ProfessionalId createdBy) {
        var now = OffsetDateTime.now(ZoneOffset.UTC);
        var aggregate = new MentalStatusExam(MentalStatusExamId.generate(), encounterId, appearance, behavior, attitude, consciousness, orientation, attention, memory, speech, mood, affect, thoughtProcess, thoughtContent, perception, judgment, insight, psychomotorActivity, observations, createdBy, now);
        aggregate.recordEvent(new MentalStatusExamRegisteredEvent(aggregate.id, now.toLocalDateTime()));
        return aggregate;
    }
    public static MentalStatusExam restore(MentalStatusExamId id, EncounterId encounterId, String appearance, String behavior, String attitude, String consciousness, String orientation, String attention, String memory, String speech, String mood, String affect, String thoughtProcess, String thoughtContent, String perception, String judgment, String insight, String psychomotorActivity, String observations, ProfessionalId createdBy, OffsetDateTime createdAt) {
        return new MentalStatusExam(id, encounterId, appearance, behavior, attitude, consciousness, orientation, attention, memory, speech, mood, affect, thoughtProcess, thoughtContent, perception, judgment, insight, psychomotorActivity, observations, createdBy, createdAt);
    }
    public void update(EncounterId encounterId, String appearance, String behavior, String attitude, String consciousness, String orientation, String attention, String memory, String speech, String mood, String affect, String thoughtProcess, String thoughtContent, String perception, String judgment, String insight, String psychomotorActivity, String observations) {
        setDetails(encounterId, appearance, behavior, attitude, consciousness, orientation, attention, memory, speech, mood, affect, thoughtProcess, thoughtContent, perception, judgment, insight, psychomotorActivity, observations);
        recordEvent(new MentalStatusExamUpdatedEvent(id, OffsetDateTime.now(ZoneOffset.UTC).toLocalDateTime()));
    }
    private void setDetails(EncounterId encounterId, String appearance, String behavior, String attitude, String consciousness, String orientation, String attention, String memory, String speech, String mood, String affect, String thoughtProcess, String thoughtContent, String perception, String judgment, String insight, String psychomotorActivity, String observations) {
        // Validar todos los datos antes de modificar el estado.
        Objects.requireNonNull(encounterId, "encounterId es obligatorio");
        Objects.requireNonNull(appearance, "appearance es obligatorio");
        Objects.requireNonNull(behavior, "behavior es obligatorio");
        Objects.requireNonNull(attitude, "attitude es obligatorio");
        Objects.requireNonNull(consciousness, "consciousness es obligatorio");
        Objects.requireNonNull(orientation, "orientation es obligatorio");
        Objects.requireNonNull(attention, "attention es obligatorio");
        Objects.requireNonNull(memory, "memory es obligatorio");
        Objects.requireNonNull(speech, "speech es obligatorio");
        Objects.requireNonNull(mood, "mood es obligatorio");
        Objects.requireNonNull(affect, "affect es obligatorio");
        Objects.requireNonNull(thoughtProcess, "thoughtProcess es obligatorio");
        Objects.requireNonNull(thoughtContent, "thoughtContent es obligatorio");
        Objects.requireNonNull(perception, "perception es obligatorio");
        Objects.requireNonNull(judgment, "judgment es obligatorio");
        Objects.requireNonNull(insight, "insight es obligatorio");
        Objects.requireNonNull(psychomotorActivity, "psychomotorActivity es obligatorio");
        Objects.requireNonNull(observations, "observations es obligatorio");
        this.encounterId = encounterId;
        this.appearance = appearance;
        this.behavior = behavior;
        this.attitude = attitude;
        this.consciousness = consciousness;
        this.orientation = orientation;
        this.attention = attention;
        this.memory = memory;
        this.speech = speech;
        this.mood = mood;
        this.affect = affect;
        this.thoughtProcess = thoughtProcess;
        this.thoughtContent = thoughtContent;
        this.perception = perception;
        this.judgment = judgment;
        this.insight = insight;
        this.psychomotorActivity = psychomotorActivity;
        this.observations = observations;
    }
    public MentalStatusExamId id() { return id; }
    public EncounterId encounterId() { return encounterId; }
    public String appearance() { return appearance; }
    public String behavior() { return behavior; }
    public String attitude() { return attitude; }
    public String consciousness() { return consciousness; }
    public String orientation() { return orientation; }
    public String attention() { return attention; }
    public String memory() { return memory; }
    public String speech() { return speech; }
    public String mood() { return mood; }
    public String affect() { return affect; }
    public String thoughtProcess() { return thoughtProcess; }
    public String thoughtContent() { return thoughtContent; }
    public String perception() { return perception; }
    public String judgment() { return judgment; }
    public String insight() { return insight; }
    public String psychomotorActivity() { return psychomotorActivity; }
    public String observations() { return observations; }
    public OffsetDateTime createdAt() { return createdAt; }
    public ProfessionalId createdBy() { return createdBy; }
}
