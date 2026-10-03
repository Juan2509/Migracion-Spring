package com.migracion.rangel.application.professionalstudy;
import java.util.*;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.professionalstudy.command.*;
import com.migracion.rangel.application.professionalstudy.usecase.*;
import com.migracion.rangel.application.professionalstudy.exception.ProfessionalStudyNotFoundApplicationException;
import com.migracion.rangel.application.study.exception.StudyNotFoundApplicationException;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
import com.migracion.rangel.application.country.exception.CountryNotFoundApplicationException;
import com.migracion.rangel.domain.professionalstudy.model.aggregate.ProfessionalStudy;
import com.migracion.rangel.domain.professionalstudy.model.valueobject.ProfessionalStudyId;
import com.migracion.rangel.domain.professionalstudy.port.repository.ProfessionalStudyRepository;
import com.migracion.rangel.domain.study.model.aggregate.Study;
import com.migracion.rangel.domain.study.model.valueobject.StudyId;
import com.migracion.rangel.domain.study.port.repository.StudyRepository;
import com.migracion.rangel.domain.professional.model.aggregate.Professional;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.domain.country.model.aggregate.Country;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
import com.migracion.rangel.domain.country.port.repository.CountryRepository;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
class ProfessionalStudyUseCasesTest {
    @Test void fullCrudAllowsRepeatedAssociationAndNullableResolution() {
        var f = new Fixture();
        var first = f.register.execute(f.command(f.study.id(), f.professional.id(), f.country.id()));
        var second = f.register.execute(f.command(f.study.id(), f.professional.id(), f.country.id()));
        assertNotEquals(first.id(), second.id());
        var id = new ProfessionalStudyId(first.id());
        var get = new GetProfessionalStudyByIdUseCase(f.repository);
        assertEquals(first, get.execute(id));
        var changed = f.update.execute(new UpdateProfessionalStudyCommand(id, f.study.id(), f.professional.id(), "Nuevo", "Otra", true, "RES-1", f.country.id()));
        assertEquals(first.createdAt(), changed.createdAt()); assertEquals("RES-1", changed.resolutionNumber());
        changed = f.update.execute(new UpdateProfessionalStudyCommand(id, f.study.id(), f.professional.id(), "Nuevo", "Otra", false, null, f.country.id()));
        assertNull(changed.resolutionNumber()); assertFalse(changed.isValid());
        assertEquals(2, new ListProfessionalStudyUseCase(f.repository).execute().size());
        var delete = new DeleteProfessionalStudyUseCase(f.repository);
        assertEquals(id, delete.execute(id).id());
        assertThrows(ProfessionalStudyNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(ProfessionalStudyNotFoundApplicationException.class, () -> delete.execute(id));
    }
    @Test void eachMissingParentPreventsRegistration() {
        var f = new Fixture();
        assertThrows(StudyNotFoundApplicationException.class, () -> f.register.execute(f.command(StudyId.generate(), f.professional.id(), f.country.id())));
        assertThrows(ProfessionalNotFoundApplicationException.class, () -> f.register.execute(f.command(f.study.id(), ProfessionalId.generate(), f.country.id())));
        assertThrows(CountryNotFoundApplicationException.class, () -> f.register.execute(f.command(f.study.id(), f.professional.id(), CountryId.generate())));
        assertEquals(0, f.repository.saves); assertTrue(f.repository.findAll().isEmpty());
    }
    @Test void eachMissingParentPreventsPartialUpdate() {
        var f = new Fixture();
        var original = f.register.execute(f.command(f.study.id(), f.professional.id(), f.country.id()));
        var id = new ProfessionalStudyId(original.id());
        assertThrows(StudyNotFoundApplicationException.class, () -> f.update.execute(new UpdateProfessionalStudyCommand(id, StudyId.generate(), f.professional.id(), "Nuevo", "Otra", true, "R", f.country.id())));
        assertThrows(ProfessionalNotFoundApplicationException.class, () -> f.update.execute(new UpdateProfessionalStudyCommand(id, f.study.id(), ProfessionalId.generate(), "Nuevo", "Otra", true, "R", f.country.id())));
        assertThrows(CountryNotFoundApplicationException.class, () -> f.update.execute(new UpdateProfessionalStudyCommand(id, f.study.id(), f.professional.id(), "Nuevo", "Otra", true, "R", CountryId.generate())));
        assertEquals(original, new GetProfessionalStudyByIdUseCase(f.repository).execute(id));
        assertEquals(1, f.repository.saves);
    }
    // Dobles de puertos padre: solo se permite consultar su identidad.
    private static <T> T parent(Class<T> type, Object id, Object aggregate) {
        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, (proxy, method, args) -> {
            if (method.getName().equals("findById")) return Objects.equals(id, args[0]) ? Optional.of(aggregate) : Optional.empty();
            throw new UnsupportedOperationException(method.getName());
        }));
    }
    private static class Fixture {
        final Study study = Study.register("Psicología");
        final Country country = Country.register("Colombia", "CO", "País", true, "+57");
        final Professional professional = Professional.register(DocumentTypeId.generate(), "DOC1", "Ana", "Pérez", ProfessionalTypeId.generate(), "LIC1", true, CityMunicipalityId.generate());
        final Repository repository = new Repository();
        final StudyRepository studies = parent(StudyRepository.class, study.id(), study);
        final ProfessionalRepository professionals = parent(ProfessionalRepository.class, professional.id(), professional);
        final CountryRepository countries = parent(CountryRepository.class, country.id(), country);
        final RegisterProfessionalStudyUseCase register = new RegisterProfessionalStudyUseCase(repository, studies, professionals, countries);
        final UpdateProfessionalStudyUseCase update = new UpdateProfessionalStudyUseCase(repository, studies, professionals, countries);
        RegisterProfessionalStudyCommand command(StudyId s, ProfessionalId p, CountryId c) {
            return new RegisterProfessionalStudyCommand(s, p, "Título", "Universidad", false, null, c);
        }
    }
    private static class Repository implements ProfessionalStudyRepository {
        final Map<ProfessionalStudyId, ProfessionalStudy> values = new LinkedHashMap<>();
        int saves;
        public ProfessionalStudy save(ProfessionalStudy item) { saves++; values.put(item.id(), item); return item; }
        public Optional<ProfessionalStudy> findById(ProfessionalStudyId id) { return Optional.ofNullable(values.get(id)); }
        public List<ProfessionalStudy> findAll() { return List.copyOf(values.values()); }
        public void delete(ProfessionalStudy item) { values.remove(item.id()); }
    }
}
