package com.migracion.rangel.infrastructure.documenttype.adapters.out.persistence.repositories;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.documenttype.model.aggregate.DocumentType;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.documenttype.port.repository.DocumentTypeRepository;
import com.migracion.rangel.infrastructure.documenttype.adapters.out.persistence.mappers.DocumentTypePersistenceMapper;
public class DocumentTypeRepositoryAdapter implements DocumentTypeRepository {
    private final DocumentTypeJpaRepository repository;
    private final DocumentTypePersistenceMapper mapper;
    public DocumentTypeRepositoryAdapter(DocumentTypeJpaRepository repository, DocumentTypePersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public DocumentType save(DocumentType aggregate) { return mapper.toDomain(repository.save(mapper.toJpa(aggregate))); }
    public Optional<DocumentType> findById(DocumentTypeId id) { return repository.findById(id.value()).map(mapper::toDomain); }
    public List<DocumentType> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void delete(DocumentType aggregate) { repository.deleteById(aggregate.id().value()); }
    public boolean existsByCode(String value) { return repository.existsByCode(value); }
    public boolean existsByCodeAndIdNot(String value, DocumentTypeId id) {
        return repository.existsByCodeAndIdNot(value, id.value());
    }
}
