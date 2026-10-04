package com.migracion.rangel.infrastructure.messagetype.adapters.out.persistence.entity;
import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.*;
@Entity
@Table(name = "message_types")
public class MessageTypeJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name_type", nullable = false, length = 50)
    private String nameType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    public MessageTypeJpaEntity() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNameType() { return nameType; }
    public void setNameType(String nameType) { this.nameType = nameType; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
