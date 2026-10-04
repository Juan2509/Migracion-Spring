package com.migracion.rangel.application.chatparticipant.command;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.sendertype.model.valueobject.SenderTypeId;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.chatparticipant.model.valueobject.ChatParticipantId;
public record UpdateChatParticipantCommand(ChatParticipantId id, ChatConversationId conversationId, SenderTypeId participantTypeId, PatientId patientId, ProfessionalId professionalId) {}

