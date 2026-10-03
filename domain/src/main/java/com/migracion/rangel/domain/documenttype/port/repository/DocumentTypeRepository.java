package com.migracion.rangel.domain.documenttype.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.documenttype.model.aggregate.DocumentType;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
public interface DocumentTypeRepository {
    DocumentType save(DocumentType aggregate);
    Optional<DocumentType> findById(DocumentTypeId id);
    List<DocumentType> findAll();
    void delete(DocumentType aggregate);
    boolean existsByCode(String value);
    boolean existsByCodeAndIdNot(String value, DocumentTypeId id);
}
