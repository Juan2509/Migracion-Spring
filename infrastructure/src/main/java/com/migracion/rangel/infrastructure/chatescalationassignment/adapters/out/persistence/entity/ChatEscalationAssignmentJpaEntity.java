package com.migracion.rangel.infrastructure.chatescalationassignment.adapters.out.persistence.entity;
import java.util.UUID;
import java.time.LocalDateTime;
import jakarta.persistence.*;
@Entity
@Table(name = "chat_escalation_assignments")
public class ChatEscalationAssignmentJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "escalation_id", nullable = false)
    private UUID escalationId;
    @Column(name = "professional_id", nullable = false)
    private UUID professionalId;
    @Column(name = "assigned_at", nullable = false)
    private LocalDateTime assignedAt;
    public ChatEscalationAssignmentJpaEntity() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getEscalationId() { return escalationId; }
    public void setEscalationId(UUID escalationId) { this.escalationId = escalationId; }
    public UUID getProfessionalId() { return professionalId; }
    public void setProfessionalId(UUID professionalId) { this.professionalId = professionalId; }
    public LocalDateTime getAssignedAt() { return assignedAt; }
    public void setAssignedAt(LocalDateTime assignedAt) { this.assignedAt = assignedAt; }
}
