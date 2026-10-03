package com.migracion.rangel.application.relationshiptype.usecase;
import com.migracion.rangel.domain.relationshiptype.port.repository.RelationshipTypeRepository;
import com.migracion.rangel.domain.relationshiptype.model.valueobject.RelationshipTypeId;
import com.migracion.rangel.application.relationshiptype.dto.RelationshipTypeResponse;
import com.migracion.rangel.application.relationshiptype.exception.RelationshipTypeNotFoundApplicationException;
import com.migracion.rangel.application.relationshiptype.exception.DuplicateRelationshipTypeApplicationException;
import com.migracion.rangel.application.relationshiptype.command.RegisterRelationshipTypeCommand;
import com.migracion.rangel.domain.relationshiptype.model.aggregate.RelationshipType;
public class RegisterRelationshipTypeUseCase {
    private final RelationshipTypeRepository repository;
    public RegisterRelationshipTypeUseCase(RelationshipTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public RelationshipTypeResponse execute(RegisterRelationshipTypeCommand command) {
        var aggregate = RelationshipType.register(command.description());
        if (repository.existsByDescription(command.description())) { throw new DuplicateRelationshipTypeApplicationException(); }
        return RelationshipTypeResponse.from(repository.save(aggregate));
    }
}
