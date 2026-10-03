package com.migracion.rangel.application.documenttype.usecase;
import com.migracion.rangel.domain.documenttype.port.repository.DocumentTypeRepository;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.application.documenttype.dto.DocumentTypeResponse;
import com.migracion.rangel.application.documenttype.exception.DocumentTypeNotFoundApplicationException;
import com.migracion.rangel.application.documenttype.exception.DuplicateDocumentTypeApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.documenttype.event.DocumentTypeDeletedEvent;
public class DeleteDocumentTypeUseCase {
    private final DocumentTypeRepository repository;
    public DeleteDocumentTypeUseCase(DocumentTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public DocumentTypeDeletedEvent execute(DocumentTypeId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new DocumentTypeNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new DocumentTypeDeletedEvent(id, LocalDateTime.now());
    }
}
