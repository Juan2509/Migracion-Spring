package com.migracion.rangel.application.patient.dto;
import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.patient.model.aggregate.Patient;
public record PatientResponse(UUID id, UUID documentTypeId, String documentNumber, String firstName, String middleName, String lastName, String secondLastName, LocalDate birthDate, UUID biologicalSexId, UUID genderIdentity, String email, String phone, String address, Boolean active, UUID cityId, LocalDateTime createdAt, UUID createdBy, LocalDateTime updatedAt, UUID updatedBy) {
    public static PatientResponse from(Patient patient) {
        return new PatientResponse(patient.id().value(), patient.documentTypeId().value(), patient.documentNumber(), patient.firstName(), patient.middleName(), patient.lastName(), patient.secondLastName(), patient.birthDate(), patient.biologicalSexId().value(), patient.genderIdentity().value(), patient.email(), patient.phone(), patient.address(), patient.active(), patient.cityId().value(),
                patient.createdAt(), patient.createdBy() == null ? null : patient.createdBy().value(),
                patient.updatedAt(), patient.updatedBy() == null ? null : patient.updatedBy().value());
    }
}
