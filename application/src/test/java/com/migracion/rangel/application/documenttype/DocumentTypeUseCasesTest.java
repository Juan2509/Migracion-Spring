package com.migracion.rangel.application.documenttype;

import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.application.documenttype.command.*;
import com.migracion.rangel.application.documenttype.exception.DocumentTypeNotFoundApplicationException;
import com.migracion.rangel.application.documenttype.exception.DuplicateDocumentTypeApplicationException;
import com.migracion.rangel.application.documenttype.usecase.*;
import com.migracion.rangel.domain.documenttype.model.aggregate.DocumentType;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.documenttype.port.repository.DocumentTypeRepository;

class DocumentTypeUseCasesTest {
    @Test
    void crudPreservesAuditAndReportsMissingRecord() {
        var repository = new MemoryRepository();
        var register = new RegisterDocumentTypeUseCase(repository);
        var get = new GetDocumentTypeByIdUseCase(repository);
        var list = new ListDocumentTypeUseCase(repository);
        var update = new UpdateDocumentTypeUseCase(repository);
        var delete = new DeleteDocumentTypeUseCase(repository);
        var response = register.execute(new RegisterDocumentTypeCommand("CC", "Cédula", true));
        var id = new DocumentTypeId(response.id());
        assertEquals(response, get.execute(id));
        assertEquals(List.of(response), list.execute());
        var changed = update.execute(new UpdateDocumentTypeCommand(id, "Otro code", "Otro name", false));
        assertEquals(response.id(), changed.id());
        assertEquals(response.createdAt(), changed.createdAt());
        assertEquals("Otro code", changed.code());
        assertEquals("Otro name", changed.name());
        assertEquals(false, changed.active());
        assertEquals(changed, get.execute(id));
        assertEquals(id, delete.execute(id).id());
        assertTrue(list.execute().isEmpty());
        assertThrows(DocumentTypeNotFoundApplicationException.class, () -> get.execute(id));
        assertThrows(DocumentTypeNotFoundApplicationException.class, () -> delete.execute(id));
        assertThrows(DocumentTypeNotFoundApplicationException.class, () -> update.execute(new UpdateDocumentTypeCommand(id, "CC", "Cédula", true)));
    }

    @Test
    void uniquenessRejectsDuplicatesWithoutChangingRecordsAndAllowsOwnValue() {
        var repository = new MemoryRepository();
        var register = new RegisterDocumentTypeUseCase(repository);
        var update = new UpdateDocumentTypeUseCase(repository);
        var first = register.execute(new RegisterDocumentTypeCommand("CC", "Cédula", true));
        var second = register.execute(new RegisterDocumentTypeCommand("Otro code", "Otro name", false));
        assertThrows(DuplicateDocumentTypeApplicationException.class,
                () -> register.execute(new RegisterDocumentTypeCommand("CC", "Cédula", true)));
        var firstId = new DocumentTypeId(first.id());
        var secondId = new DocumentTypeId(second.id());
        var unchanged = update.execute(new UpdateDocumentTypeCommand(firstId, "CC", "Cédula", true));
        assertEquals(first.code(), unchanged.code());
        assertThrows(DuplicateDocumentTypeApplicationException.class,
                () -> update.execute(new UpdateDocumentTypeCommand(secondId, first.code(), first.name(), first.active())));
        assertEquals(second, new GetDocumentTypeByIdUseCase(repository).execute(secondId));
        assertEquals(2, repository.findAll().size());
        assertEquals(3, repository.saves);
    }

    private static class MemoryRepository implements DocumentTypeRepository {
        private final Map<DocumentTypeId, DocumentType> values = new LinkedHashMap<>();
        private int saves;
        public DocumentType save(DocumentType aggregate) { saves++; values.put(aggregate.id(), aggregate); return aggregate; }
        public Optional<DocumentType> findById(DocumentTypeId id) { return Optional.ofNullable(values.get(id)); }
        public List<DocumentType> findAll() { return List.copyOf(values.values()); }
        public void delete(DocumentType aggregate) { values.remove(aggregate.id()); }
        public boolean existsByCode(String value) {
            return values.values().stream().anyMatch(aggregate -> aggregate.code().equals(value));
        }
        public boolean existsByCodeAndIdNot(String value, DocumentTypeId id) {
            return values.values().stream().anyMatch(aggregate -> !aggregate.id().equals(id) && aggregate.code().equals(value));
        }
    }
}
