package com.migracion.rangel.application.documenttype.usecase;
import com.migracion.rangel.domain.documenttype.port.repository.DocumentTypeRepository;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.application.documenttype.dto.DocumentTypeResponse;
import com.migracion.rangel.application.documenttype.exception.DocumentTypeNotFoundApplicationException;
import com.migracion.rangel.application.documenttype.exception.DuplicateDocumentTypeApplicationException;
import com.migracion.rangel.application.documenttype.command.UpdateDocumentTypeCommand;
public class UpdateDocumentTypeUseCase {
    private final DocumentTypeRepository repository;
    public UpdateDocumentTypeUseCase(DocumentTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public DocumentTypeResponse execute(UpdateDocumentTypeCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new DocumentTypeNotFoundApplicationException(id));
        if (repository.existsByCodeAndIdNot(command.code(), id)) { throw new DuplicateDocumentTypeApplicationException(); }
        aggregate.update(command.code(), command.name(), command.active());
        return DocumentTypeResponse.from(repository.save(aggregate));
    }
}
