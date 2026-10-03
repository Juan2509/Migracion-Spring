package com.migracion.rangel.infrastructure.relationshiptype.adapters.out.persistence.entity;
import java.util.UUID;
import jakarta.persistence.*;
@Entity
@Table(name = "relationship_types", uniqueConstraints = @UniqueConstraint(name = "uq_relationship_types_description", columnNames = "description"))
public class RelationshipTypeJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "description", nullable = false, length = 50)
    private String description;

    public RelationshipTypeJpaEntity() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
