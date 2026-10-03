package com.migracion.rangel.application.documenttype.usecase;
import com.migracion.rangel.domain.documenttype.port.repository.DocumentTypeRepository;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.application.documenttype.dto.DocumentTypeResponse;
import com.migracion.rangel.application.documenttype.exception.DocumentTypeNotFoundApplicationException;
import com.migracion.rangel.application.documenttype.exception.DuplicateDocumentTypeApplicationException;
import java.util.List;
public class ListDocumentTypeUseCase {
    private final DocumentTypeRepository repository;
    public ListDocumentTypeUseCase(DocumentTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<DocumentTypeResponse> execute() { return repository.findAll().stream().map(DocumentTypeResponse::from).toList(); }
}
