package com.migracion.rangel.application.encounter.dto;
import java.util.UUID;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.encounter.model.aggregate.Encounter;
public record EncounterResponse(UUID id, UUID clinicalRecordId, UUID professionalId, UUID encounterTypeId, OffsetDateTime startedAt, OffsetDateTime endedAt, String reasonForVisit, String currentCondition, UUID modalityId, UUID statusId, OffsetDateTime createdAt, UUID createdBy, OffsetDateTime updatedAt, UUID updatedBy) {
    public static EncounterResponse from(Encounter aggregate) {
        return new EncounterResponse(aggregate.id().value(), aggregate.clinicalRecordId().value(), aggregate.professionalId().value(), aggregate.encounterTypeId().value(), aggregate.startedAt(), aggregate.endedAt(), aggregate.reasonForVisit(), aggregate.currentCondition(), aggregate.modalityId().value(), aggregate.statusId().value(), aggregate.createdAt(), aggregate.createdBy().value(), aggregate.updatedAt(), aggregate.updatedBy().value());
    }
}
