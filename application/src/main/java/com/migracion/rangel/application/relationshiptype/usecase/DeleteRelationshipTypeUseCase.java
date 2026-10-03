package com.migracion.rangel.application.relationshiptype.usecase;
import com.migracion.rangel.domain.relationshiptype.port.repository.RelationshipTypeRepository;
import com.migracion.rangel.domain.relationshiptype.model.valueobject.RelationshipTypeId;
import com.migracion.rangel.application.relationshiptype.dto.RelationshipTypeResponse;
import com.migracion.rangel.application.relationshiptype.exception.RelationshipTypeNotFoundApplicationException;
import com.migracion.rangel.application.relationshiptype.exception.DuplicateRelationshipTypeApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.relationshiptype.event.RelationshipTypeDeletedEvent;
public class DeleteRelationshipTypeUseCase {
    private final RelationshipTypeRepository repository;
    public DeleteRelationshipTypeUseCase(RelationshipTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public RelationshipTypeDeletedEvent execute(RelationshipTypeId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new RelationshipTypeNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new RelationshipTypeDeletedEvent(id, LocalDateTime.now());
    }
}
