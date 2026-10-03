package com.migracion.rangel.domain.patientcontact;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.patientcontact.model.aggregate.PatientContact;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.relationshiptype.model.valueobject.RelationshipTypeId;
class PatientContactTest {
    @Test void flagsAndReferencesCanChangeWithoutAddingAuditFields() {
        var item = PatientContact.register(ContactId.generate(), PatientId.generate(), false, false, RelationshipTypeId.generate());
        var id = item.id(); assertEquals(1, item.domainEvents().size()); item.clearDomainEvents();
        var contact = ContactId.generate(); var patient = PatientId.generate(); var relation = RelationshipTypeId.generate();
        item.update(contact, patient, true, true, relation);
        assertEquals(id, item.id()); assertEquals(contact, item.contactId()); assertEquals(patient, item.patientId());
        assertEquals(relation, item.relationshipTypeId()); assertTrue(item.isPrimaryContact()); assertTrue(item.isEmergencyContact());
        assertEquals(1, item.domainEvents().size());
        var restored = PatientContact.restore(id, contact, patient, true, true, relation);
        assertTrue(restored.domainEvents().isEmpty());
    }
    @Test void nullFlagsOrReferencesCannotPartiallyModifyAssociation() {
        var contact = ContactId.generate(); var patient = PatientId.generate(); var relation = RelationshipTypeId.generate();
        var item = PatientContact.register(contact, patient, false, false, relation);
        assertThrows(NullPointerException.class, () -> item.update(ContactId.generate(), patient, true, true, null));
        assertThrows(NullPointerException.class, () -> item.update(contact, patient, null, true, relation));
        assertThrows(NullPointerException.class, () -> item.update(contact, patient, true, null, relation));
        assertThrows(NullPointerException.class, () -> item.update(null, patient, true, true, relation));
        assertThrows(NullPointerException.class, () -> item.update(contact, null, true, true, relation));
        assertEquals(contact, item.contactId()); assertEquals(relation, item.relationshipTypeId());
        assertFalse(item.isPrimaryContact()); assertFalse(item.isEmergencyContact()); assertEquals(1, item.domainEvents().size());
    }
}
