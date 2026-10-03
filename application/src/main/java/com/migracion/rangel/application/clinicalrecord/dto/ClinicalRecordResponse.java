package com.migracion.rangel.application.clinicalrecord.dto;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.clinicalrecord.model.aggregate.ClinicalRecord;
public record ClinicalRecordResponse(UUID id, UUID patientId, LocalDateTime creationDate, String recordNumber, OffsetDateTime openedAt, OffsetDateTime closedAt, UUID statusId, UUID createdBy, OffsetDateTime createdAt) {
    public static ClinicalRecordResponse from(ClinicalRecord aggregate) {
        return new ClinicalRecordResponse(aggregate.id().value(), aggregate.patientId().value(), aggregate.creationDate(), aggregate.recordNumber(), aggregate.openedAt(), aggregate.closedAt(), aggregate.statusId().value(), aggregate.createdBy().value(), aggregate.createdAt());
    }
}
