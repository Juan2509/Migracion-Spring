package com.migracion.rangel.application.patientcontact.dto;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.patientcontact.model.aggregate.PatientContact;
public record PatientContactResponse(UUID id, UUID contactId, UUID patientId, Boolean isPrimaryContact, Boolean isEmergencyContact, UUID relationshipTypeId) {
    public static PatientContactResponse from(PatientContact aggregate) {
        return new PatientContactResponse(aggregate.id().value(), aggregate.contactId().value(), aggregate.patientId().value(), aggregate.isPrimaryContact(), aggregate.isEmergencyContact(), aggregate.relationshipTypeId().value());
    }
}
