package com.migracion.rangel.application.relationshiptype.usecase;
import com.migracion.rangel.domain.relationshiptype.port.repository.RelationshipTypeRepository;
import com.migracion.rangel.domain.relationshiptype.model.valueobject.RelationshipTypeId;
import com.migracion.rangel.application.relationshiptype.dto.RelationshipTypeResponse;
import com.migracion.rangel.application.relationshiptype.exception.RelationshipTypeNotFoundApplicationException;
import com.migracion.rangel.application.relationshiptype.exception.DuplicateRelationshipTypeApplicationException;
import com.migracion.rangel.application.relationshiptype.command.UpdateRelationshipTypeCommand;
public class UpdateRelationshipTypeUseCase {
    private final RelationshipTypeRepository repository;
    public UpdateRelationshipTypeUseCase(RelationshipTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public RelationshipTypeResponse execute(UpdateRelationshipTypeCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new RelationshipTypeNotFoundApplicationException(id));
        if (repository.existsByDescriptionAndIdNot(command.description(), id)) { throw new DuplicateRelationshipTypeApplicationException(); }
        aggregate.update(command.description());
        return RelationshipTypeResponse.from(repository.save(aggregate));
    }
}
