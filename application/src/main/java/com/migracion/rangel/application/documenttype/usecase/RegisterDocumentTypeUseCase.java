package com.migracion.rangel.application.documenttype.usecase;
import com.migracion.rangel.domain.documenttype.port.repository.DocumentTypeRepository;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.application.documenttype.dto.DocumentTypeResponse;
import com.migracion.rangel.application.documenttype.exception.DocumentTypeNotFoundApplicationException;
import com.migracion.rangel.application.documenttype.exception.DuplicateDocumentTypeApplicationException;
import com.migracion.rangel.application.documenttype.command.RegisterDocumentTypeCommand;
import com.migracion.rangel.domain.documenttype.model.aggregate.DocumentType;
public class RegisterDocumentTypeUseCase {
    private final DocumentTypeRepository repository;
    public RegisterDocumentTypeUseCase(DocumentTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public DocumentTypeResponse execute(RegisterDocumentTypeCommand command) {
        var aggregate = DocumentType.register(command.code(), command.name(), command.active());
        if (repository.existsByCode(command.code())) { throw new DuplicateDocumentTypeApplicationException(); }
        return DocumentTypeResponse.from(repository.save(aggregate));
    }
}
