package com.migracion.rangel.application.study;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.study.command.*;
import com.migracion.rangel.application.study.exception.StudyNotFoundApplicationException;
import com.migracion.rangel.application.study.usecase.*;
import com.migracion.rangel.domain.study.model.aggregate.Study;
import com.migracion.rangel.domain.study.model.valueobject.StudyId;
import com.migracion.rangel.domain.study.port.repository.StudyRepository;

class StudyUseCasesTest {
    @Test
    void crudPreservesAuditAndReportsMissingStudy() {
        var repository = new MemoryRepository();
        var register = new RegisterStudyUseCase(repository);
        var get = new GetStudyByIdUseCase(repository);
        var list = new ListStudyUseCase(repository);
        var update = new UpdateStudyUseCase(repository);
        var delete = new DeleteStudyUseCase(repository);
        var response = register.execute(new RegisterStudyCommand("Psicología"));
        var id = new StudyId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        var changed = update.execute(new UpdateStudyCommand(id, "Psiquiatría"));
        assertEquals(response.id(), changed.id());
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals("Psiquiatría", changed.name());
        assertEquals(changed, get.execute(id));
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(StudyNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(StudyNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(StudyNotFoundApplicationException.class, () -> update.execute(new UpdateStudyCommand(id, "Psicología")));
    }
    @Test
    void repeatedNamesAreAllowedOnRegisterAndUpdate() {
        var repository = new MemoryRepository();
        var register = new RegisterStudyUseCase(repository);
        var first = register.execute(new RegisterStudyCommand("Psicología"));
        var second = register.execute(new RegisterStudyCommand("Psicología"));
        assertNotEquals(first.id(), second.id());
        var third = register.execute(new RegisterStudyCommand("Medicina"));
        var changed = new UpdateStudyUseCase(repository).execute(
                new UpdateStudyCommand(new StudyId(third.id()), "Psicología"));
        assertEquals("Psicología", changed.name());
        assertEquals(3, new ListStudyUseCase(repository).execute().size());
    }
    private static class MemoryRepository implements StudyRepository {
        private final Map<StudyId, Study> values = new LinkedHashMap<>();
        public Study save(Study aggregate) { values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<Study> findById(StudyId id) { return Optional.ofNullable(values.get(id)); }
        public List<Study> findAll() { return List.copyOf(values.values()); }
        public void delete(Study aggregate) { values.remove(aggregate.id()); }
    }
}
