package com.migracion.rangel.application.patientcontact.command;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.relationshiptype.model.valueobject.RelationshipTypeId;
import com.migracion.rangel.domain.patientcontact.model.valueobject.PatientContactId;
public record RegisterPatientContactCommand(ContactId contactId, PatientId patientId, Boolean isPrimaryContact, Boolean isEmergencyContact, RelationshipTypeId relationshipTypeId) {}

