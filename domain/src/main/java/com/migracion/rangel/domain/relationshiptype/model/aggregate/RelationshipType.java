package com.migracion.rangel.domain.relationshiptype.model.aggregate;

import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.relationshiptype.model.valueobject.RelationshipTypeId;
import com.migracion.rangel.domain.relationshiptype.event.RelationshipTypeRegisteredEvent;
import com.migracion.rangel.domain.relationshiptype.event.RelationshipTypeUpdatedEvent;

public final class RelationshipType extends AggregateRoot {
    private final RelationshipTypeId id;
    private String description;

    private RelationshipType(RelationshipTypeId id, String description) {
        this.id = Objects.requireNonNull(id, "El ID es obligatorio");
        setDetails(description);
    }
    public static RelationshipType register(String description) {
        var now = LocalDateTime.now();
        var aggregate = new RelationshipType(RelationshipTypeId.generate(), description);
        aggregate.recordEvent(new RelationshipTypeRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static RelationshipType restore(RelationshipTypeId id, String description) {
        return new RelationshipType(id, description);
    }
    public void update(String description) {
        setDetails(description);
        var occurredOn = LocalDateTime.now();
        recordEvent(new RelationshipTypeUpdatedEvent(id, occurredOn));
    }
    private void setDetails(String description) {
        validateText(description, 50, "description");
        this.description = description;
    }
    private static void validateText(String value, int limit, String field) {
        Objects.requireNonNull(value, field + " es obligatorio");
        if (value.codePointCount(0, value.length()) > limit) {
            throw new IllegalArgumentException(field + " supera " + limit + " caracteres");
        }
    }
    public RelationshipTypeId id() { return id; }
    public String description() { return description; }
}
