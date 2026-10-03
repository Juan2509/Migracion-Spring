package com.migracion.rangel.application.professional;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.professional.command.*;
import com.migracion.rangel.application.professional.exception.*;
import com.migracion.rangel.application.professional.usecase.*;
import com.migracion.rangel.domain.professional.model.aggregate.Professional;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.domain.documenttype.model.aggregate.DocumentType;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.documenttype.port.repository.DocumentTypeRepository;
import com.migracion.rangel.application.documenttype.exception.DocumentTypeNotFoundApplicationException;
import com.migracion.rangel.domain.professionaltype.model.aggregate.ProfessionalType;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.domain.professionaltype.port.repository.ProfessionalTypeRepository;
import com.migracion.rangel.application.professionaltype.exception.ProfessionalTypeNotFoundApplicationException;
import com.migracion.rangel.domain.citymunicipality.model.aggregate.CityMunicipality;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.citymunicipality.port.repository.CityMunicipalityRepository;
import com.migracion.rangel.application.citymunicipality.exception.CityMunicipalityNotFoundApplicationException;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;

class ProfessionalUseCasesTest {
    @Test
    void completeCrudPreservesFieldsAndReportsMissingProfessional() {
        var fixture = new Fixture();
        var original = fixture.register.execute(command(fixture, 1));
        var id = new ProfessionalId(original.id());
        var get = new GetProfessionalByIdUseCase(fixture.professionals);
        var list = new ListProfessionalUseCase(fixture.professionals);
        assertEquals(original, get.execute(id));
        assertEquals(List.of(original), list.execute());
        var command = command(fixture, 2);
        var changed = fixture.update.execute(new UpdateProfessionalCommand(id, command.documentTypeId(), command.documentNumber(), command.firstName(), command.lastName(), command.professionalType(), command.licenseNumber(), command.active(), command.cityId()));
        assertEquals(original.id(), changed.id());
        assertEquals(original.createdAt(), changed.createdAt());
        assertEquals(command.documentTypeId().value(), changed.documentTypeId());
        assertEquals(command.documentNumber(), changed.documentNumber());
        assertEquals(command.firstName(), changed.firstName());
        assertEquals(command.lastName(), changed.lastName());
        assertEquals(command.professionalType().value(), changed.professionalType());
        assertEquals(command.licenseNumber(), changed.licenseNumber());
        assertEquals(command.active(), changed.active());
        assertEquals(command.cityId().value(), changed.cityId());
        assertEquals(changed, get.execute(id));
        var delete = new DeleteProfessionalUseCase(fixture.professionals);
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(ProfessionalNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(ProfessionalNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(ProfessionalNotFoundApplicationException.class,
                () -> fixture.update.execute(new UpdateProfessionalCommand(id, command.documentTypeId(), command.documentNumber(), command.firstName(), command.lastName(), command.professionalType(), command.licenseNumber(), command.active(), command.cityId())));
    }

    @Test
    void missingReferencesCannotRegisterOrChangeProfessional() {
        {
            var fixture = new Fixture();
            var command = command(fixture, 1);
            var original = fixture.register.execute(command);
            var id = new ProfessionalId(original.id());
            var missing = DocumentTypeId.generate();
            var invalid = new RegisterProfessionalCommand(missing, command.documentNumber(), command.firstName(), command.lastName(), command.professionalType(), command.licenseNumber(), command.active(), command.cityId());
            assertThrows(DocumentTypeNotFoundApplicationException.class, () -> fixture.register.execute(invalid));
            assertThrows(DocumentTypeNotFoundApplicationException.class,
                    () -> fixture.update.execute(new UpdateProfessionalCommand(id, invalid.documentTypeId(), invalid.documentNumber(), invalid.firstName(), invalid.lastName(), invalid.professionalType(), invalid.licenseNumber(), invalid.active(), invalid.cityId())));
            assertEquals(original, new GetProfessionalByIdUseCase(fixture.professionals).execute(id));
            assertEquals(1, fixture.professionals.saves);
        }
        {
            var fixture = new Fixture();
            var command = command(fixture, 1);
            var original = fixture.register.execute(command);
            var id = new ProfessionalId(original.id());
            var missing = ProfessionalTypeId.generate();
            var invalid = new RegisterProfessionalCommand(command.documentTypeId(), command.documentNumber(), command.firstName(), command.lastName(), missing, command.licenseNumber(), command.active(), command.cityId());
            assertThrows(ProfessionalTypeNotFoundApplicationException.class, () -> fixture.register.execute(invalid));
            assertThrows(ProfessionalTypeNotFoundApplicationException.class,
                    () -> fixture.update.execute(new UpdateProfessionalCommand(id, invalid.documentTypeId(), invalid.documentNumber(), invalid.firstName(), invalid.lastName(), invalid.professionalType(), invalid.licenseNumber(), invalid.active(), invalid.cityId())));
            assertEquals(original, new GetProfessionalByIdUseCase(fixture.professionals).execute(id));
            assertEquals(1, fixture.professionals.saves);
        }
        {
            var fixture = new Fixture();
            var command = command(fixture, 1);
            var original = fixture.register.execute(command);
            var id = new ProfessionalId(original.id());
            var missing = CityMunicipalityId.generate();
            var invalid = new RegisterProfessionalCommand(command.documentTypeId(), command.documentNumber(), command.firstName(), command.lastName(), command.professionalType(), command.licenseNumber(), command.active(), missing);
            assertThrows(CityMunicipalityNotFoundApplicationException.class, () -> fixture.register.execute(invalid));
            assertThrows(CityMunicipalityNotFoundApplicationException.class,
                    () -> fixture.update.execute(new UpdateProfessionalCommand(id, invalid.documentTypeId(), invalid.documentNumber(), invalid.firstName(), invalid.lastName(), invalid.professionalType(), invalid.licenseNumber(), invalid.active(), invalid.cityId())));
            assertEquals(original, new GetProfessionalByIdUseCase(fixture.professionals).execute(id));
            assertEquals(1, fixture.professionals.saves);
        }
    }

    @Test
    void allFourUniqueFieldsRejectOtherRecordsAndAllowOwnValues() {
        {
            var fixture = new Fixture();
            var firstCommand = command(fixture, 1);
            var first = fixture.register.execute(firstCommand);
            var secondCommand = command(fixture, 2);
            var second = fixture.register.execute(secondCommand);
            var candidate = command(fixture, 3);
            var duplicate = new RegisterProfessionalCommand(candidate.documentTypeId(), firstCommand.documentNumber(), candidate.firstName(), candidate.lastName(), candidate.professionalType(), candidate.licenseNumber(), candidate.active(), candidate.cityId());
            var error = assertThrows(DuplicateProfessionalApplicationException.class, () -> fixture.register.execute(duplicate));
            assertTrue(error.getMessage().contains("documentNumber"));
            var secondId = new ProfessionalId(second.id());
            assertThrows(DuplicateProfessionalApplicationException.class,
                    () -> fixture.update.execute(new UpdateProfessionalCommand(secondId, duplicate.documentTypeId(), duplicate.documentNumber(), duplicate.firstName(), duplicate.lastName(), duplicate.professionalType(), duplicate.licenseNumber(), duplicate.active(), duplicate.cityId())));
            assertEquals(second, new GetProfessionalByIdUseCase(fixture.professionals).execute(secondId));
            var own = fixture.update.execute(new UpdateProfessionalCommand(new ProfessionalId(first.id()),
                    firstCommand.documentTypeId(), firstCommand.documentNumber(), firstCommand.firstName(), firstCommand.lastName(), firstCommand.professionalType(), firstCommand.licenseNumber(), firstCommand.active(), firstCommand.cityId()));
            assertEquals(first.documentNumber(), own.documentNumber());
            assertEquals(3, fixture.professionals.saves);
        }
        {
            var fixture = new Fixture();
            var firstCommand = command(fixture, 1);
            var first = fixture.register.execute(firstCommand);
            var secondCommand = command(fixture, 2);
            var second = fixture.register.execute(secondCommand);
            var candidate = command(fixture, 3);
            var duplicate = new RegisterProfessionalCommand(candidate.documentTypeId(), candidate.documentNumber(), firstCommand.firstName(), candidate.lastName(), candidate.professionalType(), candidate.licenseNumber(), candidate.active(), candidate.cityId());
            var error = assertThrows(DuplicateProfessionalApplicationException.class, () -> fixture.register.execute(duplicate));
            assertTrue(error.getMessage().contains("firstName"));
            var secondId = new ProfessionalId(second.id());
            assertThrows(DuplicateProfessionalApplicationException.class,
                    () -> fixture.update.execute(new UpdateProfessionalCommand(secondId, duplicate.documentTypeId(), duplicate.documentNumber(), duplicate.firstName(), duplicate.lastName(), duplicate.professionalType(), duplicate.licenseNumber(), duplicate.active(), duplicate.cityId())));
            assertEquals(second, new GetProfessionalByIdUseCase(fixture.professionals).execute(secondId));
            var own = fixture.update.execute(new UpdateProfessionalCommand(new ProfessionalId(first.id()),
                    firstCommand.documentTypeId(), firstCommand.documentNumber(), firstCommand.firstName(), firstCommand.lastName(), firstCommand.professionalType(), firstCommand.licenseNumber(), firstCommand.active(), firstCommand.cityId()));
            assertEquals(first.firstName(), own.firstName());
            assertEquals(3, fixture.professionals.saves);
        }
        {
            var fixture = new Fixture();
            var firstCommand = command(fixture, 1);
            var first = fixture.register.execute(firstCommand);
            var secondCommand = command(fixture, 2);
            var second = fixture.register.execute(secondCommand);
            var candidate = command(fixture, 3);
            var duplicate = new RegisterProfessionalCommand(candidate.documentTypeId(), candidate.documentNumber(), candidate.firstName(), firstCommand.lastName(), candidate.professionalType(), candidate.licenseNumber(), candidate.active(), candidate.cityId());
            var error = assertThrows(DuplicateProfessionalApplicationException.class, () -> fixture.register.execute(duplicate));
            assertTrue(error.getMessage().contains("lastName"));
            var secondId = new ProfessionalId(second.id());
            assertThrows(DuplicateProfessionalApplicationException.class,
                    () -> fixture.update.execute(new UpdateProfessionalCommand(secondId, duplicate.documentTypeId(), duplicate.documentNumber(), duplicate.firstName(), duplicate.lastName(), duplicate.professionalType(), duplicate.licenseNumber(), duplicate.active(), duplicate.cityId())));
            assertEquals(second, new GetProfessionalByIdUseCase(fixture.professionals).execute(secondId));
            var own = fixture.update.execute(new UpdateProfessionalCommand(new ProfessionalId(first.id()),
                    firstCommand.documentTypeId(), firstCommand.documentNumber(), firstCommand.firstName(), firstCommand.lastName(), firstCommand.professionalType(), firstCommand.licenseNumber(), firstCommand.active(), firstCommand.cityId()));
            assertEquals(first.lastName(), own.lastName());
            assertEquals(3, fixture.professionals.saves);
        }
        {
            var fixture = new Fixture();
            var firstCommand = command(fixture, 1);
            var first = fixture.register.execute(firstCommand);
            var secondCommand = command(fixture, 2);
            var second = fixture.register.execute(secondCommand);
            var candidate = command(fixture, 3);
            var duplicate = new RegisterProfessionalCommand(candidate.documentTypeId(), candidate.documentNumber(), candidate.firstName(), candidate.lastName(), candidate.professionalType(), firstCommand.licenseNumber(), candidate.active(), candidate.cityId());
            var error = assertThrows(DuplicateProfessionalApplicationException.class, () -> fixture.register.execute(duplicate));
            assertTrue(error.getMessage().contains("licenseNumber"));
            var secondId = new ProfessionalId(second.id());
            assertThrows(DuplicateProfessionalApplicationException.class,
                    () -> fixture.update.execute(new UpdateProfessionalCommand(secondId, duplicate.documentTypeId(), duplicate.documentNumber(), duplicate.firstName(), duplicate.lastName(), duplicate.professionalType(), duplicate.licenseNumber(), duplicate.active(), duplicate.cityId())));
            assertEquals(second, new GetProfessionalByIdUseCase(fixture.professionals).execute(secondId));
            var own = fixture.update.execute(new UpdateProfessionalCommand(new ProfessionalId(first.id()),
                    firstCommand.documentTypeId(), firstCommand.documentNumber(), firstCommand.firstName(), firstCommand.lastName(), firstCommand.professionalType(), firstCommand.licenseNumber(), firstCommand.active(), firstCommand.cityId()));
            assertEquals(first.licenseNumber(), own.licenseNumber());
            assertEquals(3, fixture.professionals.saves);
        }
    }

    @Test
    void updateCanChangeAllThreeReferences() {
        var fixture = new Fixture();
        var original = fixture.register.execute(command(fixture, 1));
        var document = fixture.documents.save(DocumentType.register("CE", "Otro documento", true));
        var type = fixture.types.save(ProfessionalType.register("Psiquiatra"));
        var city = fixture.cities.save(CityMunicipality.register("Bogotá", "BOG", "Ciudad", true, StateRegionId.generate()));
        var command = command(fixture, 2);
        var changed = fixture.update.execute(new UpdateProfessionalCommand(new ProfessionalId(original.id()),
                document.id(), command.documentNumber(), command.firstName(), command.lastName(), type.id(), command.licenseNumber(), command.active(), city.id()));
        assertEquals(document.id().value(), changed.documentTypeId());
        assertEquals(type.id().value(), changed.professionalType());
        assertEquals(city.id().value(), changed.cityId());
        assertEquals(original.createdAt(), changed.createdAt());
    }

    private static RegisterProfessionalCommand command(Fixture fixture, int seed) {
        return new RegisterProfessionalCommand(fixture.document.id(), "documentNumber-" + seed, "firstName-" + seed, "lastName-" + seed, fixture.type.id(), "licenseNumber-" + seed, true, fixture.city.id());
    }
    private static class Fixture {
        final Professionals professionals = new Professionals();
        final Documents documents = new Documents();
        final Types types = new Types();
        final Cities cities = new Cities();
        final DocumentType document = documents.save(DocumentType.register("CC", "Cédula", true));
        final ProfessionalType type = types.save(ProfessionalType.register("Psicólogo"));
        final CityMunicipality city = cities.save(CityMunicipality.register("Medellín", "MED", "Ciudad", true, StateRegionId.generate()));
        final RegisterProfessionalUseCase register = new RegisterProfessionalUseCase(professionals, documents, types, cities);
        final UpdateProfessionalUseCase update = new UpdateProfessionalUseCase(professionals, documents, types, cities);
    }
    private static class Professionals implements ProfessionalRepository {
        private final Map<ProfessionalId, Professional> values = new LinkedHashMap<>();
        private int saves;
        public Professional save(Professional professional) { saves++; values.put(professional.id(), professional); return professional; }
        public Optional<Professional> findById(ProfessionalId id) { return Optional.ofNullable(values.get(id)); }
        public List<Professional> findAll() { return List.copyOf(values.values()); }
        public void delete(Professional professional) { values.remove(professional.id()); }
        public boolean existsByDocumentNumber(String value) {
            return values.values().stream().anyMatch(p -> p.documentNumber().equals(value));
        }
        public boolean existsByDocumentNumberAndIdNot(String value, ProfessionalId id) {
            return values.values().stream().anyMatch(p -> !p.id().equals(id) && p.documentNumber().equals(value));
        }
        public boolean existsByFirstName(String value) {
            return values.values().stream().anyMatch(p -> p.firstName().equals(value));
        }
        public boolean existsByFirstNameAndIdNot(String value, ProfessionalId id) {
            return values.values().stream().anyMatch(p -> !p.id().equals(id) && p.firstName().equals(value));
        }
        public boolean existsByLastName(String value) {
            return values.values().stream().anyMatch(p -> p.lastName().equals(value));
        }
        public boolean existsByLastNameAndIdNot(String value, ProfessionalId id) {
            return values.values().stream().anyMatch(p -> !p.id().equals(id) && p.lastName().equals(value));
        }
        public boolean existsByLicenseNumber(String value) {
            return values.values().stream().anyMatch(p -> p.licenseNumber().equals(value));
        }
        public boolean existsByLicenseNumberAndIdNot(String value, ProfessionalId id) {
            return values.values().stream().anyMatch(p -> !p.id().equals(id) && p.licenseNumber().equals(value));
        }
    }
    private static class Documents implements DocumentTypeRepository {
        private final Map<DocumentTypeId, DocumentType> values = new HashMap<>();
        public DocumentType save(DocumentType aggregate) { values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<DocumentType> findById(DocumentTypeId id) { return Optional.ofNullable(values.get(id)); }
        public List<DocumentType> findAll() { return List.copyOf(values.values()); }
        public void delete(DocumentType aggregate) { values.remove(aggregate.id()); }
        public boolean existsByCode(String value) { throw new UnsupportedOperationException(); }
        public boolean existsByCodeAndIdNot(String value, DocumentTypeId id) { throw new UnsupportedOperationException(); }
    }
    private static class Types implements ProfessionalTypeRepository {
        private final Map<ProfessionalTypeId, ProfessionalType> values = new HashMap<>();
        public ProfessionalType save(ProfessionalType aggregate) { values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<ProfessionalType> findById(ProfessionalTypeId id) { return Optional.ofNullable(values.get(id)); }
        public List<ProfessionalType> findAll() { return List.copyOf(values.values()); }
        public void delete(ProfessionalType aggregate) { values.remove(aggregate.id()); }
        public boolean existsByName(String value) { throw new UnsupportedOperationException(); }
        public boolean existsByNameAndIdNot(String value, ProfessionalTypeId id) { throw new UnsupportedOperationException(); }
    }
    private static class Cities implements CityMunicipalityRepository {
        private final Map<CityMunicipalityId, CityMunicipality> values = new HashMap<>();
        public CityMunicipality save(CityMunicipality aggregate) { values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<CityMunicipality> findById(CityMunicipalityId id) { return Optional.ofNullable(values.get(id)); }
        public List<CityMunicipality> findAll() { return List.copyOf(values.values()); }
        public void delete(CityMunicipality aggregate) { values.remove(aggregate.id()); }

    }
}
