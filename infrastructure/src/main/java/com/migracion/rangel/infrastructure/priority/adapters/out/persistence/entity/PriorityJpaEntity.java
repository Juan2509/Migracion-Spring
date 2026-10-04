package com.migracion.rangel.infrastructure.priority.adapters.out.persistence.entity;
import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.*;
@Entity
@Table(name = "priorities")
public class PriorityJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name_priority", nullable = false, length = 50)
    private String namePriority;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    public PriorityJpaEntity() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNamePriority() { return namePriority; }
    public void setNamePriority(String namePriority) { this.namePriority = namePriority; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
