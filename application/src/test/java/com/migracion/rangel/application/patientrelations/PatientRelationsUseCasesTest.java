package com.migracion.rangel.application.patientrelations;
import java.util.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.patientcontact.command.*;
import com.migracion.rangel.application.patientcontact.usecase.*;
import com.migracion.rangel.application.patientcontact.exception.PatientContactNotFoundApplicationException;
import com.migracion.rangel.application.patientallergy.command.*;
import com.migracion.rangel.application.patientallergy.usecase.*;
import com.migracion.rangel.application.patientallergy.exception.PatientAllergyNotFoundApplicationException;
import com.migracion.rangel.application.patient.exception.PatientNotFoundApplicationException;
import com.migracion.rangel.application.contact.exception.ContactNotFoundApplicationException;
import com.migracion.rangel.application.relationshiptype.exception.RelationshipTypeNotFoundApplicationException;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
import com.migracion.rangel.domain.patientcontact.model.aggregate.PatientContact;
import com.migracion.rangel.domain.patientcontact.model.valueobject.PatientContactId;
import com.migracion.rangel.domain.patientcontact.port.repository.PatientContactRepository;
import com.migracion.rangel.domain.patientallergy.model.aggregate.PatientAllergy;
import com.migracion.rangel.domain.patientallergy.model.valueobject.PatientAllergyId;
import com.migracion.rangel.domain.patientallergy.port.repository.PatientAllergyRepository;
import com.migracion.rangel.domain.patient.model.aggregate.Patient;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.patient.port.repository.PatientRepository;
import com.migracion.rangel.domain.contact.model.aggregate.Contact;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.domain.contact.port.repository.ContactRepository;
import com.migracion.rangel.domain.relationshiptype.model.aggregate.RelationshipType;
import com.migracion.rangel.domain.relationshiptype.model.valueobject.RelationshipTypeId;
import com.migracion.rangel.domain.relationshiptype.port.repository.RelationshipTypeRepository;
import com.migracion.rangel.domain.professional.model.aggregate.Professional;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;

class PatientRelationsUseCasesTest {
    @Test void contactCrudCanChangeEveryReferenceAndBothFlags() {
        var f = new Fixture();
        var original = f.registerContact.execute(f.contactCommand());
        var id = new PatientContactId(original.id());
        var get = new GetPatientContactByIdUseCase(f.contactsRepository);
        assertEquals(original, get.execute(id));
        assertEquals(List.of(original), new ListPatientContactUseCase(f.contactsRepository).execute());
        var changed = f.updateContact.execute(new UpdatePatientContactCommand(id, f.otherContact.id(), f.otherPatient.id(), true, true, f.otherRelationship.id()));
        assertEquals(f.otherContact.id().value(), changed.contactId()); assertEquals(f.otherPatient.id().value(), changed.patientId());
        assertEquals(f.otherRelationship.id().value(), changed.relationshipTypeId());
        assertTrue(changed.isPrimaryContact()); assertTrue(changed.isEmergencyContact());
        var delete = new DeletePatientContactUseCase(f.contactsRepository);
        assertEquals(id, delete.execute(id).id()); assertTrue(f.contactsRepository.findAll().isEmpty());
        assertThrows(PatientContactNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(PatientContactNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(PatientContactNotFoundApplicationException.class, () -> f.updateContact.execute(
                new UpdatePatientContactCommand(id, f.contact.id(), f.patient.id(), false, false, f.relationship.id())));
    }
    @Test void eachMissingContactAssociationParentPreventsSaveOrMutation() {
        var exceptions = List.of(ContactNotFoundApplicationException.class, PatientNotFoundApplicationException.class, RelationshipTypeNotFoundApplicationException.class);
        for (int i = 0; i < 3; i++) {
            var f = new Fixture();
            var original = f.registerContact.execute(f.contactCommand());
            var id = new PatientContactId(original.id());
            var contact = i == 0 ? ContactId.generate() : f.contact.id();
            var patient = i == 1 ? PatientId.generate() : f.patient.id();
            var relationship = i == 2 ? RelationshipTypeId.generate() : f.relationship.id();
            assertThrows(exceptions.get(i), () -> f.registerContact.execute(
                    new RegisterPatientContactCommand(contact, patient, true, true, relationship)));
            assertThrows(exceptions.get(i), () -> f.updateContact.execute(
                    new UpdatePatientContactCommand(id, contact, patient, true, true, relationship)));
            assertEquals(original, new GetPatientContactByIdUseCase(f.contactsRepository).execute(id));
            assertEquals(1, f.contactsRepository.saves);
        }
    }
    @Test void repeatedAssociationsAndMultiplePrimaryEmergencyContactsAreAllowed() {
        var f = new Fixture();
        var first = f.registerContact.execute(f.contactCommand());
        var second = f.registerContact.execute(f.contactCommand());
        assertNotEquals(first.id(), second.id()); assertFalse(first.isPrimaryContact());
        for (var item : List.of(first, second)) {
            f.updateContact.execute(new UpdatePatientContactCommand(new PatientContactId(item.id()), f.contact.id(), f.patient.id(), true, true, f.relationship.id()));
        }
        assertEquals(2, f.contactsRepository.findAll().size());
    }
    @Test void allergyCrudPreservesCreationAndSupportsNullableReactionAndChangedRecorder() {
        var f = new Fixture();
        var original = f.registerAllergy.execute(f.allergyCommand());
        var id = new PatientAllergyId(original.id());
        var get = new GetPatientAllergyByIdUseCase(f.allergiesRepository);
        assertEquals(original, get.execute(id));
        assertEquals(List.of(original), new ListPatientAllergyUseCase(f.allergiesRepository).execute());
        var time = f.recorded.plusDays(1);
        var changed = f.updateAllergy.execute(new UpdatePatientAllergyCommand(id, f.otherPatient.id(), "Polen", "Estornudos", "Leve", true, time, f.otherProfessional.id()));
        assertEquals(original.createdAt(), changed.createdAt()); assertEquals(f.otherPatient.id().value(), changed.patientId());
        assertEquals(f.otherProfessional.id().value(), changed.recordedBy()); assertEquals(time, changed.recordedAt());
        assertEquals("Estornudos", changed.reaction());
        changed = f.updateAllergy.execute(new UpdatePatientAllergyCommand(id, f.otherPatient.id(), "Polen", null, "Leve", false, time, f.otherProfessional.id()));
        assertNull(changed.reaction()); assertFalse(changed.active());
        var delete = new DeletePatientAllergyUseCase(f.allergiesRepository);
        assertEquals(id, delete.execute(id).id()); assertTrue(f.allergiesRepository.findAll().isEmpty());
        assertThrows(PatientAllergyNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(PatientAllergyNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(PatientAllergyNotFoundApplicationException.class, () -> f.updateAllergy.execute(
                new UpdatePatientAllergyCommand(id, f.patient.id(), "Polen", null, "Leve", true, time, f.professional.id())));
    }
    @Test void missingAllergyPatientOrRecorderPreventsRegistrationAndPartialUpdate() {
        var exceptions = List.of(PatientNotFoundApplicationException.class, ProfessionalNotFoundApplicationException.class);
        for (int i = 0; i < 2; i++) {
            var f = new Fixture();
            var original = f.registerAllergy.execute(f.allergyCommand());
            var id = new PatientAllergyId(original.id());
            var patient = i == 0 ? PatientId.generate() : f.patient.id();
            var recorder = i == 1 ? ProfessionalId.generate() : f.professional.id();
            assertThrows(exceptions.get(i), () -> f.registerAllergy.execute(
                    new RegisterPatientAllergyCommand(patient, "Polen", "Nueva", "Leve", true, f.recorded, recorder)));
            assertThrows(exceptions.get(i), () -> f.updateAllergy.execute(
                    new UpdatePatientAllergyCommand(id, patient, "Polen", "Nueva", "Leve", true, f.recorded, recorder)));
            assertEquals(original, new GetPatientAllergyByIdUseCase(f.allergiesRepository).execute(id));
            assertEquals(1, f.allergiesRepository.saves);
        }
    }
    @Test void repeatedAllergiesAreAllowedAndInvalidSeverityCannotModifyExistingRecord() {
        var f = new Fixture();
        var first = f.registerAllergy.execute(f.allergyCommand());
        var second = f.registerAllergy.execute(f.allergyCommand());
        assertNotEquals(first.id(), second.id());
        var id = new PatientAllergyId(first.id());
        assertThrows(IllegalArgumentException.class, () -> f.updateAllergy.execute(
                new UpdatePatientAllergyCommand(id, f.patient.id(), "Nueva", "Nueva", "x".repeat(21), true, f.recorded, f.professional.id())));
        assertEquals(first, new GetPatientAllergyByIdUseCase(f.allergiesRepository).execute(id));
        assertEquals(2, f.allergiesRepository.saves);
    }
    private static <T> T parent(Class<T> type, Map<?, ?> values) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, (proxy, method, args) -> {
            if (method.getName().equals("findById")) return Optional.ofNullable(values.get(args[0]));
            throw new UnsupportedOperationException(method.getName());
        }));
    }
    private static Patient patient(String email) {
        return Patient.register(DocumentTypeId.generate(), "DOC1", "Ana", null, "Pérez", null,
                LocalDate.of(1990, 1, 2), GenderId.generate(), GenderId.generate(), email, "300123", "Calle 1", true, CityMunicipalityId.generate(), null);
    }
    private static Professional professional(String document) {
        return Professional.register(DocumentTypeId.generate(), document, "Nombre", "Apellido", ProfessionalTypeId.generate(), "LIC1", true, CityMunicipalityId.generate());
    }
    private static class Fixture {
        final Patient patient = patient("ana@example.com"), otherPatient = patient("otra@example.com");
        final Professional professional = professional("PRO1"), otherProfessional = professional("PRO2");
        final Contact contact = Contact.register("Ana", "ana@example.com", "Notas", CityMunicipalityId.generate(), professional.id());
        final Contact otherContact = Contact.register("Luis", "luis@example.com", "Notas", CityMunicipalityId.generate(), professional.id());
        final RelationshipType relationship = RelationshipType.register("Familia"), otherRelationship = RelationshipType.register("Amigo");
        final OffsetDateTime recorded = OffsetDateTime.parse("2026-10-03T09:30:00-05:00");
        final PatientRepository patients = parent(PatientRepository.class, Map.of(patient.id(), patient, otherPatient.id(), otherPatient));
        final ProfessionalRepository professionals = parent(ProfessionalRepository.class, Map.of(professional.id(), professional, otherProfessional.id(), otherProfessional));
        final ContactRepository contacts = parent(ContactRepository.class, Map.of(contact.id(), contact, otherContact.id(), otherContact));
        final RelationshipTypeRepository relationships = parent(RelationshipTypeRepository.class, Map.of(relationship.id(), relationship, otherRelationship.id(), otherRelationship));
        final ContactsRepository contactsRepository = new ContactsRepository();
        final AllergiesRepository allergiesRepository = new AllergiesRepository();
        final RegisterPatientContactUseCase registerContact = new RegisterPatientContactUseCase(contactsRepository, contacts, patients, relationships);
        final UpdatePatientContactUseCase updateContact = new UpdatePatientContactUseCase(contactsRepository, contacts, patients, relationships);
        final RegisterPatientAllergyUseCase registerAllergy = new RegisterPatientAllergyUseCase(allergiesRepository, patients, professionals);
        final UpdatePatientAllergyUseCase updateAllergy = new UpdatePatientAllergyUseCase(allergiesRepository, patients, professionals);
        RegisterPatientContactCommand contactCommand() { return new RegisterPatientContactCommand(contact.id(), patient.id(), false, false, relationship.id()); }
        RegisterPatientAllergyCommand allergyCommand() { return new RegisterPatientAllergyCommand(patient.id(), "Penicilina", null, "Alta", false, recorded, professional.id()); }
    }
    private static class ContactsRepository implements PatientContactRepository {
        final Map<PatientContactId, PatientContact> values = new LinkedHashMap<>();
        int saves;
        public PatientContact save(PatientContact item) { saves++; values.put(item.id(), item); return item; }
        public Optional<PatientContact> findById(PatientContactId id) { return Optional.ofNullable(values.get(id)); }
        public List<PatientContact> findAll() { return List.copyOf(values.values()); }
        public void delete(PatientContact item) { values.remove(item.id()); }
    }
    private static class AllergiesRepository implements PatientAllergyRepository {
        final Map<PatientAllergyId, PatientAllergy> values = new LinkedHashMap<>();
        int saves;
        public PatientAllergy save(PatientAllergy item) { saves++; values.put(item.id(), item); return item; }
        public Optional<PatientAllergy> findById(PatientAllergyId id) { return Optional.ofNullable(values.get(id)); }
        public List<PatientAllergy> findAll() { return List.copyOf(values.values()); }
        public void delete(PatientAllergy item) { values.remove(item.id()); }
    }
}

