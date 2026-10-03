package com.migracion.rangel.application.relationshiptype.usecase;
import com.migracion.rangel.domain.relationshiptype.port.repository.RelationshipTypeRepository;
import com.migracion.rangel.domain.relationshiptype.model.valueobject.RelationshipTypeId;
import com.migracion.rangel.application.relationshiptype.dto.RelationshipTypeResponse;
import com.migracion.rangel.application.relationshiptype.exception.RelationshipTypeNotFoundApplicationException;
import com.migracion.rangel.application.relationshiptype.exception.DuplicateRelationshipTypeApplicationException;
import java.util.List;
public class ListRelationshipTypeUseCase {
    private final RelationshipTypeRepository repository;
    public ListRelationshipTypeUseCase(RelationshipTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<RelationshipTypeResponse> execute() { return repository.findAll().stream().map(RelationshipTypeResponse::from).toList(); }
}
