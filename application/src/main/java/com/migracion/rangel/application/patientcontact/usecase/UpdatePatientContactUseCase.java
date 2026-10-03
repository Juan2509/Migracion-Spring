package com.migracion.rangel.application.patientcontact.usecase;
import java.util.Objects;
import com.migracion.rangel.domain.patientcontact.model.aggregate.PatientContact;
import com.migracion.rangel.domain.patientcontact.port.repository.PatientContactRepository;
import com.migracion.rangel.application.patientcontact.command.UpdatePatientContactCommand;
import com.migracion.rangel.application.patientcontact.dto.PatientContactResponse;
import com.migracion.rangel.application.patientcontact.exception.PatientContactNotFoundApplicationException;
import com.migracion.rangel.domain.contact.port.repository.ContactRepository;
import com.migracion.rangel.application.contact.exception.ContactNotFoundApplicationException;
import com.migracion.rangel.domain.patient.port.repository.PatientRepository;
import com.migracion.rangel.application.patient.exception.PatientNotFoundApplicationException;
import com.migracion.rangel.domain.relationshiptype.port.repository.RelationshipTypeRepository;
import com.migracion.rangel.application.relationshiptype.exception.RelationshipTypeNotFoundApplicationException;
public class UpdatePatientContactUseCase {
    private final PatientContactRepository repository;
    private final ContactRepository contacts;
    private final PatientRepository patients;
    private final RelationshipTypeRepository relationships;
    public UpdatePatientContactUseCase(PatientContactRepository repository, ContactRepository contacts, PatientRepository patients, RelationshipTypeRepository relationships) {
        this.repository = Objects.requireNonNull(repository);
        this.contacts = Objects.requireNonNull(contacts);
        this.patients = Objects.requireNonNull(patients);
        this.relationships = Objects.requireNonNull(relationships);
    }
    public PatientContactResponse execute(UpdatePatientContactCommand command) {
        var aggregate = repository.findById(command.id()).orElseThrow(() -> new PatientContactNotFoundApplicationException(command.id()));
        contacts.findById(command.contactId()).orElseThrow(() -> new ContactNotFoundApplicationException(command.contactId()));
        patients.findById(command.patientId()).orElseThrow(() -> new PatientNotFoundApplicationException(command.patientId()));
        relationships.findById(command.relationshipTypeId()).orElseThrow(() -> new RelationshipTypeNotFoundApplicationException(command.relationshipTypeId()));
        aggregate.update(command.contactId(), command.patientId(), command.isPrimaryContact(), command.isEmergencyContact(), command.relationshipTypeId());
        return PatientContactResponse.from(repository.save(aggregate));
    }
}
