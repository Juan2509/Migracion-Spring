package com.migracion.rangel.domain.patientcontact.model.aggregate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.relationshiptype.model.valueobject.RelationshipTypeId;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.patientcontact.model.valueobject.PatientContactId;
import com.migracion.rangel.domain.patientcontact.event.PatientContactRegisteredEvent;
import com.migracion.rangel.domain.patientcontact.event.PatientContactUpdatedEvent;
public final class PatientContact extends AggregateRoot {
    private final PatientContactId id;
    private ContactId contactId;
    private PatientId patientId;
    private Boolean isPrimaryContact;
    private Boolean isEmergencyContact;
    private RelationshipTypeId relationshipTypeId;

    private PatientContact(PatientContactId id, ContactId contactId, PatientId patientId, Boolean isPrimaryContact, Boolean isEmergencyContact, RelationshipTypeId relationshipTypeId) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(contactId, patientId, isPrimaryContact, isEmergencyContact, relationshipTypeId);

    }
    public static PatientContact register(ContactId contactId, PatientId patientId, Boolean isPrimaryContact, Boolean isEmergencyContact, RelationshipTypeId relationshipTypeId) {
        var now = LocalDateTime.now();
        var aggregate = new PatientContact(PatientContactId.generate(), contactId, patientId, isPrimaryContact, isEmergencyContact, relationshipTypeId);
        aggregate.recordEvent(new PatientContactRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static PatientContact restore(PatientContactId id, ContactId contactId, PatientId patientId, Boolean isPrimaryContact, Boolean isEmergencyContact, RelationshipTypeId relationshipTypeId) {
        return new PatientContact(id, contactId, patientId, isPrimaryContact, isEmergencyContact, relationshipTypeId);
    }
    public void update(ContactId contactId, PatientId patientId, Boolean isPrimaryContact, Boolean isEmergencyContact, RelationshipTypeId relationshipTypeId) {
        setDetails(contactId, patientId, isPrimaryContact, isEmergencyContact, relationshipTypeId);
        var now = LocalDateTime.now();

        recordEvent(new PatientContactUpdatedEvent(id, now));
    }
    private void setDetails(ContactId contactId, PatientId patientId, Boolean isPrimaryContact, Boolean isEmergencyContact, RelationshipTypeId relationshipTypeId) {
        // Validar todos los valores antes de modificar el agregado.
        Objects.requireNonNull(contactId, "contactId es obligatorio");
        Objects.requireNonNull(patientId, "patientId es obligatorio");
        Objects.requireNonNull(isPrimaryContact, "isPrimaryContact es obligatorio");
        Objects.requireNonNull(isEmergencyContact, "isEmergencyContact es obligatorio");
        Objects.requireNonNull(relationshipTypeId, "relationshipTypeId es obligatorio");
        this.contactId = contactId;
        this.patientId = patientId;
        this.isPrimaryContact = isPrimaryContact;
        this.isEmergencyContact = isEmergencyContact;
        this.relationshipTypeId = relationshipTypeId;
    }

    public PatientContactId id() { return id; }
    public ContactId contactId() { return contactId; }
    public PatientId patientId() { return patientId; }
    public Boolean isPrimaryContact() { return isPrimaryContact; }
    public Boolean isEmergencyContact() { return isEmergencyContact; }
    public RelationshipTypeId relationshipTypeId() { return relationshipTypeId; }

}
