package com.migracion.rangel.domain.chatparticipant.model.aggregate;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.sendertype.model.valueobject.SenderTypeId;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.chatparticipant.model.valueobject.ChatParticipantId;
import com.migracion.rangel.domain.chatparticipant.event.*;
public final class ChatParticipant extends AggregateRoot {
    private final ChatParticipantId id;
    private ChatConversationId conversationId;
    private SenderTypeId participantTypeId;
    private PatientId patientId;
    private ProfessionalId professionalId;

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ChatParticipant(ChatParticipantId id, ChatConversationId conversationId, SenderTypeId participantTypeId, PatientId patientId, ProfessionalId professionalId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
        setDetails(conversationId, participantTypeId, patientId, professionalId);
    }
    public static ChatParticipant register(ChatConversationId conversationId, SenderTypeId participantTypeId, PatientId patientId, ProfessionalId professionalId) {
        var now = LocalDateTime.now();
        var aggregate = new ChatParticipant(ChatParticipantId.generate(), conversationId, participantTypeId, patientId, professionalId, now, now);
        aggregate.recordEvent(new ChatParticipantRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static ChatParticipant restore(ChatParticipantId id, ChatConversationId conversationId, SenderTypeId participantTypeId, PatientId patientId, ProfessionalId professionalId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new ChatParticipant(id, conversationId, participantTypeId, patientId, professionalId, createdAt, updatedAt);
    }
    public void update(ChatConversationId conversationId, SenderTypeId participantTypeId, PatientId patientId, ProfessionalId professionalId) {
        setDetails(conversationId, participantTypeId, patientId, professionalId);
        updatedAt = LocalDateTime.now();
        recordEvent(new ChatParticipantUpdatedEvent(id, updatedAt));
    }
    private void setDetails(ChatConversationId conversationId, SenderTypeId participantTypeId, PatientId patientId, ProfessionalId professionalId) {
        Objects.requireNonNull(conversationId, "conversationId es obligatorio");
        Objects.requireNonNull(participantTypeId, "participantTypeId es obligatorio");
        this.conversationId = conversationId;
        this.participantTypeId = participantTypeId;
        this.patientId = patientId;
        this.professionalId = professionalId;
    }
    public ChatParticipantId id() { return id; }
    public ChatConversationId conversationId() { return conversationId; }
    public SenderTypeId participantTypeId() { return participantTypeId; }
    public PatientId patientId() { return patientId; }
    public ProfessionalId professionalId() { return professionalId; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}
