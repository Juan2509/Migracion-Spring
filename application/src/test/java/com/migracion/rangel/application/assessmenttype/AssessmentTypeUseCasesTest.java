package com.migracion.rangel.application.assessmenttype;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.assessmenttype.command.*;
import com.migracion.rangel.application.assessmenttype.exception.AssessmentTypeNotFoundApplicationException;
import com.migracion.rangel.application.assessmenttype.exception.DuplicateAssessmentTypeApplicationException;
import com.migracion.rangel.application.assessmenttype.usecase.*;
import com.migracion.rangel.domain.assessmenttype.model.aggregate.AssessmentType;
import com.migracion.rangel.domain.assessmenttype.model.valueobject.AssessmentTypeId;
import com.migracion.rangel.domain.assessmenttype.port.repository.AssessmentTypeRepository;

class AssessmentTypeUseCasesTest {
    @Test
    void differentCodesAllowRepeatedNames() {
        var repository = new MemoryRepository();
        var register = new RegisterAssessmentTypeUseCase(repository);
        var first = register.execute(new RegisterAssessmentTypeCommand("FIRST", "Nombre", true, "Descripción"));
        var second = register.execute(new RegisterAssessmentTypeCommand("SECOND", "Nombre", true, "Descripción"));
        assertNotEquals(first.id(), second.id());
        assertEquals(first.name(), second.name());
        assertEquals(2, repository.findAll().size());
    }

    @Test
    void crudPreservesAuditAndReportsMissingRecord() {
        var repository = new MemoryRepository();
        var register = new RegisterAssessmentTypeUseCase(repository);
        var get = new GetAssessmentTypeByIdUseCase(repository);
        var list = new ListAssessmentTypeUseCase(repository);
        var update = new UpdateAssessmentTypeUseCase(repository);
        var delete = new DeleteAssessmentTypeUseCase(repository);
        var response = register.execute(new RegisterAssessmentTypeCommand("CC", "Cédula", true, "Descripción"));
        var id = new AssessmentTypeId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        var changed = update.execute(new UpdateAssessmentTypeCommand(id, "Otro code", "Otro name", false, "Otra descripción"));
        assertEquals(response.id(), changed.id());
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals("Otro code", changed.code());
        assertEquals("Otro name", changed.name());
        assertEquals(false, changed.active());
        assertEquals(changed, get.execute(id));
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(AssessmentTypeNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(AssessmentTypeNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(AssessmentTypeNotFoundApplicationException.class, () -> update.execute(new UpdateAssessmentTypeCommand(id, "CC", "Cédula", true, "Descripción")));
    }

    @Test
    void uniquenessRejectsDuplicatesWithoutChangingRecordsAndAllowsOwnValue() {
        var repository = new MemoryRepository();
        var register = new RegisterAssessmentTypeUseCase(repository);
        var update = new UpdateAssessmentTypeUseCase(repository);
        var first = register.execute(new RegisterAssessmentTypeCommand("CC", "Cédula", true, "Descripción"));
        var second = register.execute(new RegisterAssessmentTypeCommand("Otro code", "Otro name", false, "Otra descripción"));
        assertThrows(DuplicateAssessmentTypeApplicationException.class,
                () -> register.execute(new RegisterAssessmentTypeCommand("CC", "Cédula", true, "Descripción")));
        var firstId = new AssessmentTypeId(first.id());
        var secondId = new AssessmentTypeId(second.id());
        var unchanged = update.execute(new UpdateAssessmentTypeCommand(firstId, "CC", "Cédula", true, "Descripción"));
        assertEquals(first.code(), unchanged.code());
        assertThrows(DuplicateAssessmentTypeApplicationException.class,
                () -> update.execute(new UpdateAssessmentTypeCommand(secondId, first.code(), first.name(), first.active(), first.description())));
        assertEquals(second, new GetAssessmentTypeByIdUseCase(repository).execute(secondId));
        assertEquals(2, repository.findAll().size());
        assertEquals(3, repository.saves);
    }

    private static class MemoryRepository implements AssessmentTypeRepository {
        private final Map<AssessmentTypeId, AssessmentType> values = new LinkedHashMap<>();
        private int saves;
        public AssessmentType save(AssessmentType aggregate) { saves++; values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<AssessmentType> findById(AssessmentTypeId id) { return Optional.ofNullable(values.get(id)); }
        public List<AssessmentType> findAll() { return List.copyOf(values.values()); }
        public void delete(AssessmentType aggregate) { values.remove(aggregate.id()); }
        public boolean existsByCode(String value) {
            return values.values().stream().anyMatch(aggregate -> aggregate.code().equals(value));
        }
        public boolean existsByCodeAndIdNot(String value, AssessmentTypeId id) {
            return values.values().stream().anyMatch(aggregate -> !aggregate.id().equals(id) && aggregate.code().equals(value));
        }
    }
}
