package com.migracion.rangel.application.relationshiptype.usecase;
import com.migracion.rangel.domain.relationshiptype.port.repository.RelationshipTypeRepository;
import com.migracion.rangel.domain.relationshiptype.model.valueobject.RelationshipTypeId;
import com.migracion.rangel.application.relationshiptype.dto.RelationshipTypeResponse;
import com.migracion.rangel.application.relationshiptype.exception.RelationshipTypeNotFoundApplicationException;
import com.migracion.rangel.application.relationshiptype.exception.DuplicateRelationshipTypeApplicationException;

public class GetRelationshipTypeByIdUseCase {
    private final RelationshipTypeRepository repository;
    public GetRelationshipTypeByIdUseCase(RelationshipTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public RelationshipTypeResponse execute(RelationshipTypeId id) { return RelationshipTypeResponse.from(repository.findById(id).orElseThrow(() -> new RelationshipTypeNotFoundApplicationException(id))); }
}
