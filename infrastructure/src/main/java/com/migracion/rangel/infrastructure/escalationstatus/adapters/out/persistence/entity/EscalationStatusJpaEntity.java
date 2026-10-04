package com.migracion.rangel.infrastructure.escalationstatus.adapters.out.persistence.entity;
import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.*;
@Entity
@Table(name = "escalations_statuses")
public class EscalationStatusJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name_status", nullable = false, length = 50)
    private String nameStatus;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    public EscalationStatusJpaEntity() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNameStatus() { return nameStatus; }
    public void setNameStatus(String nameStatus) { this.nameStatus = nameStatus; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
