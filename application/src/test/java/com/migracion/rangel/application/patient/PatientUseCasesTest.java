package com.migracion.rangel.application.patient;
import java.util.*;
import java.time.LocalDate;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.patient.command.*;
import com.migracion.rangel.application.patient.dto.PatientResponse;
import com.migracion.rangel.application.patient.usecase.*;
import com.migracion.rangel.application.patient.exception.*;
import com.migracion.rangel.application.documenttype.exception.DocumentTypeNotFoundApplicationException;
import com.migracion.rangel.application.gender.exception.GenderNotFoundApplicationException;
import com.migracion.rangel.application.citymunicipality.exception.CityMunicipalityNotFoundApplicationException;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
import com.migracion.rangel.domain.patient.model.aggregate.Patient;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.patient.port.repository.PatientRepository;
import com.migracion.rangel.domain.documenttype.model.aggregate.DocumentType;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.documenttype.port.repository.DocumentTypeRepository;
import com.migracion.rangel.domain.gender.model.aggregate.Gender;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
import com.migracion.rangel.domain.gender.port.repository.GenderRepository;
import com.migracion.rangel.domain.professional.model.aggregate.Professional;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.domain.citymunicipality.model.aggregate.CityMunicipality;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;

class PatientUseCasesTest {
    @Test void crudPreservesCreatorAndAllowsNullUpdaterAndOwnEmail() {
        var f = new Fixture();
        var original = f.register.execute(f.command("ana@example.com", f.professional.id()));
        var id = new PatientId(original.id());
        var get = new GetPatientByIdUseCase(f.repository);
        assertEquals(original, get.execute(id));
        assertEquals(List.of(original), new ListPatientUseCase(f.repository).execute());
        var changed = f.update.execute(f.updateCommand(id, original.email(), f.professional.id()));
        assertEquals(original.createdAt(), changed.createdAt()); assertEquals(original.createdBy(), changed.createdBy());
        assertEquals(f.professional.id().value(), changed.updatedBy()); assertEquals("Actualizada", changed.firstName());
        changed = f.update.execute(f.updateCommand(id, original.email(), null));
        assertNull(changed.updatedBy()); assertEquals(original.createdBy(), changed.createdBy());
        assertEquals(id, new DeletePatientUseCase(f.repository).execute(id).id());
        assertTrue(f.repository.findAll().isEmpty());
        assertThrows(PatientNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(PatientNotFoundApplicationException.class, () -> new DeletePatientUseCase(f.repository).execute(id));
        assertThrows(PatientNotFoundApplicationException.class, () -> f.update.execute(f.updateCommand(id, "new@example.com", null)));
    }
    @Test void onlyEmailIsUniqueAndDuplicateUpdateCannotMutateRecord() {
        var f = new Fixture();
        var first = f.register.execute(f.command("first@example.com", null));
        var second = f.register.execute(f.command("second@example.com", null));
        assertEquals(first.documentNumber(), second.documentNumber()); assertEquals(first.firstName(), second.firstName());
        assertNull(first.createdBy());
        assertThrows(DuplicatePatientApplicationException.class, () -> f.register.execute(f.command("first@example.com", null)));
        var id = new PatientId(second.id());
        assertThrows(DuplicatePatientApplicationException.class, () -> f.update.execute(f.updateCommand(id, "first@example.com", null)));
        assertEquals(second, new GetPatientByIdUseCase(f.repository).execute(id)); assertEquals(2, f.repository.saves);
    }
    @Test void allRequiredAndOptionalParentReferencesAreCheckedBeforeSaveOrMutation() {
        var expected = List.of(DocumentTypeNotFoundApplicationException.class, GenderNotFoundApplicationException.class,
                GenderNotFoundApplicationException.class, CityMunicipalityNotFoundApplicationException.class, ProfessionalNotFoundApplicationException.class);
        for (int i = 0; i < expected.size(); i++) {
            var f = new Fixture();
            var original = f.register.execute(f.command("ana@example.com", f.professional.id()));
            var id = new PatientId(original.id());
            var document = i == 0 ? DocumentTypeId.generate() : f.document.id();
            var sex = i == 1 ? GenderId.generate() : f.sex.id();
            var gender = i == 2 ? GenderId.generate() : f.gender.id();
            var city = i == 3 ? CityMunicipalityId.generate() : f.city.id();
            var author = i == 4 ? ProfessionalId.generate() : f.professional.id();
            var registration = new RegisterPatientCommand(document, "DOC1", "Ana", null, "Pérez", null, LocalDate.of(1990, 1, 2),
                    sex, gender, "new@example.com", "300123", "Calle 1", true, city, author);
            var update = new UpdatePatientCommand(id, document, "DOC2", "Nueva", null, "Otra", null, LocalDate.of(1991, 1, 2),
                    sex, gender, "changed@example.com", "300456", "Calle 2", false, city, author);
            assertThrows(expected.get(i), () -> f.register.execute(registration));
            assertThrows(expected.get(i), () -> f.update.execute(update));
            assertEquals(original, new GetPatientByIdUseCase(f.repository).execute(id));
            assertEquals(1, f.repository.saves);
        }
    }
    @Test void updateCanReplaceAllRequiredReferencesAndRejectInvalidDetails() {
        var f = new Fixture();
        var original = f.register.execute(f.command("ana@example.com", null));
        var id = new PatientId(original.id());
        var secondDocument = DocumentType.register("TI", "Tarjeta", true);
        var secondCity = CityMunicipality.register("Otra", "02", "Otra ciudad", true, StateRegionId.generate());
        f.documentValues.put(secondDocument.id(), secondDocument); f.cityValues.put(secondCity.id(), secondCity);
        var changed = f.update.execute(new UpdatePatientCommand(id, secondDocument.id(), "DOC2", "Otra", "Segundo", "Apellido", "Segundo apellido",
                LocalDate.of(2000, 6, 7), f.gender.id(), f.sex.id(), "other@example.com", "123", "Dirección", false, secondCity.id(), null));
        assertEquals(secondDocument.id().value(), changed.documentTypeId()); assertEquals(secondCity.id().value(), changed.cityId());
        assertEquals(f.gender.id().value(), changed.biologicalSexId()); assertEquals(f.sex.id().value(), changed.genderIdentity());
        assertThrows(IllegalArgumentException.class, () -> f.update.execute(new UpdatePatientCommand(id, secondDocument.id(), "DOC2", "x".repeat(51), null, "Apellido", null,
                LocalDate.of(2000, 6, 7), f.gender.id(), f.sex.id(), "other@example.com", "123", "Dirección", false, secondCity.id(), null)));
        assertEquals(changed, new GetPatientByIdUseCase(f.repository).execute(id)); assertEquals(2, f.repository.saves);
    }
    // Dobles de los puertos padre: no se permite consultar métodos ajenos a findById.
    private static <T> T parent(Class<T> type, Map<?, ?> values) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, (proxy, method, args) -> {
            if (method.getName().equals("findById")) return Optional.ofNullable(values.get(args[0]));
            throw new UnsupportedOperationException(method.getName());
        }));
    }
    private static class Fixture {
        final DocumentType document = DocumentType.register("CC", "Cédula", true);
        final Gender sex = Gender.register("Sexo de ejemplo");
        final Gender gender = Gender.register("Identidad de ejemplo");
        final CityMunicipality city = CityMunicipality.register("Bogotá", "01", "Ciudad", true, StateRegionId.generate());
        final Professional professional = Professional.register(document.id(), "PRO1", "Profesional", "Ejemplo", ProfessionalTypeId.generate(), "LIC1", true, city.id());
        final Map<DocumentTypeId, DocumentType> documentValues = new HashMap<>(Map.of(document.id(), document));
        final Map<CityMunicipalityId, CityMunicipality> cityValues = new HashMap<>(Map.of(city.id(), city));
        final Repository repository = new Repository();
        final DocumentTypeRepository documents = parent(DocumentTypeRepository.class, documentValues);
        final GenderRepository genders = parent(GenderRepository.class, Map.of(sex.id(), sex, gender.id(), gender));
        final CityMunicipalityRepository cities = parent(CityMunicipalityRepository.class, cityValues);
        final ProfessionalRepository professionals = parent(ProfessionalRepository.class, Map.of(professional.id(), professional));
        final RegisterPatientUseCase register = new RegisterPatientUseCase(repository, documents, genders, cities, professionals);
        final UpdatePatientUseCase update = new UpdatePatientUseCase(repository, documents, genders, cities, professionals);
        RegisterPatientCommand command(String email, ProfessionalId creator) {
            return new RegisterPatientCommand(document.id(), "DOC1", "Ana", null, "Pérez", null, LocalDate.of(1990, 1, 2),
                    sex.id(), gender.id(), email, "300123", "Calle 1", false, city.id(), creator);
        }
        UpdatePatientCommand updateCommand(PatientId id, String email, ProfessionalId updater) {
            return new UpdatePatientCommand(id, document.id(), "DOC2", "Actualizada", "Segundo", "Otra", "Apellido",
                    LocalDate.of(1991, 1, 2), sex.id(), gender.id(), email, "300456", "Calle 2", true, city.id(), updater);
        }
    }
    private static class Repository implements PatientRepository {
        final Map<PatientId, Patient> values = new LinkedHashMap<>();
        int saves;
        public Patient save(Patient item) { saves++; values.put(item.id(), item); return item; }
        public Optional<Patient> findById(PatientId id) { return Optional.ofNullable(values.get(id)); }
        public List<Patient> findAll() { return List.copyOf(values.values()); }
        public void delete(Patient item) { values.remove(item.id()); }
        public boolean existsByEmail(String email) { return values.values().stream().anyMatch(p -> p.email().equals(email)); }
        public boolean existsByEmailAndIdNot(String email, PatientId id) {
            return values.values().stream().anyMatch(p -> p.email().equals(email) && !p.id().equals(id));
        }
    }
}

