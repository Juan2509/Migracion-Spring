package com.migracion.rangel.domain.chatescalationassignment.model.aggregate;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import java.time.LocalDateTime;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.chatescalationassignment.model.valueobject.ChatEscalationAssignmentId;
import com.migracion.rangel.domain.chatescalationassignment.event.*;
public final class ChatEscalationAssignment extends AggregateRoot {
    private final ChatEscalationAssignmentId id;
    private ChatEscalationId escalationId;
    private ProfessionalId professionalId;
    private LocalDateTime assignedAt;
    private ChatEscalationAssignment(ChatEscalationAssignmentId id, ChatEscalationId escalationId, ProfessionalId professionalId, LocalDateTime assignedAt) {
        this.id = Objects.requireNonNull(id);
        setDetails(escalationId, professionalId, assignedAt);
    }
    public static ChatEscalationAssignment register(ChatEscalationId escalationId, ProfessionalId professionalId, LocalDateTime assignedAt) {
        var value = new ChatEscalationAssignment(ChatEscalationAssignmentId.generate(), escalationId, professionalId, assignedAt);
        value.recordEvent(new ChatEscalationAssignmentRegisteredEvent(value.id, LocalDateTime.now()));
        return value;
    }
    public static ChatEscalationAssignment restore(ChatEscalationAssignmentId id, ChatEscalationId escalationId, ProfessionalId professionalId, LocalDateTime assignedAt) {
        return new ChatEscalationAssignment(id, escalationId, professionalId, assignedAt);
    }
    public void update(ChatEscalationId escalationId, ProfessionalId professionalId, LocalDateTime assignedAt) {
        setDetails(escalationId, professionalId, assignedAt);
        recordEvent(new ChatEscalationAssignmentUpdatedEvent(id, LocalDateTime.now()));
    }
    private void setDetails(ChatEscalationId escalationId, ProfessionalId professionalId, LocalDateTime assignedAt) {
        Objects.requireNonNull(escalationId, "escalationId es obligatorio");
        Objects.requireNonNull(professionalId, "professionalId es obligatorio");
        Objects.requireNonNull(assignedAt, "assignedAt es obligatorio");
        this.escalationId = escalationId;
        this.professionalId = professionalId;
        this.assignedAt = assignedAt;
    }
    public ChatEscalationAssignmentId id() { return id; }
    public ChatEscalationId escalationId() { return escalationId; }
    public ProfessionalId professionalId() { return professionalId; }
    public LocalDateTime assignedAt() { return assignedAt; }
}
