package com.migracion.rangel.application.documenttype.usecase;
import com.migracion.rangel.domain.documenttype.port.repository.DocumentTypeRepository;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.application.documenttype.dto.DocumentTypeResponse;
import com.migracion.rangel.application.documenttype.exception.DocumentTypeNotFoundApplicationException;
import com.migracion.rangel.application.documenttype.exception.DuplicateDocumentTypeApplicationException;

public class GetDocumentTypeByIdUseCase {
    private final DocumentTypeRepository repository;
    public GetDocumentTypeByIdUseCase(DocumentTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public DocumentTypeResponse execute(DocumentTypeId id) { return DocumentTypeResponse.from(repository.findById(id).orElseThrow(() -> new DocumentTypeNotFoundApplicationException(id))); }
}
