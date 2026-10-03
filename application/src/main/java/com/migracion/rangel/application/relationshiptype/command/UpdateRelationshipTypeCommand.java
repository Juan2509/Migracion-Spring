package com.migracion.rangel.application.relationshiptype.command;
import com.migracion.rangel.domain.relationshiptype.model.valueobject.RelationshipTypeId;
public record UpdateRelationshipTypeCommand(RelationshipTypeId id, String description) {}
