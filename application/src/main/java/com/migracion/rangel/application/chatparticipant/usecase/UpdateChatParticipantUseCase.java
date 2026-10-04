package com.migracion.rangel.application.chatparticipant.usecase;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import com.migracion.rangel.domain.sendertype.port.repository.SenderTypeRepository;
import com.migracion.rangel.application.sendertype.exception.SenderTypeNotFoundApplicationException;
import com.migracion.rangel.domain.patient.port.repository.PatientRepository;
import com.migracion.rangel.application.patient.exception.PatientNotFoundApplicationException;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
import com.migracion.rangel.domain.chatparticipant.port.repository.ChatParticipantRepository;
import com.migracion.rangel.domain.chatparticipant.model.valueobject.ChatParticipantId;
import com.migracion.rangel.application.chatparticipant.dto.ChatParticipantResponse;
import com.migracion.rangel.application.chatparticipant.exception.ChatParticipantNotFoundApplicationException;
import com.migracion.rangel.application.chatparticipant.command.UpdateChatParticipantCommand;
public class UpdateChatParticipantUseCase {
    private final ChatParticipantRepository repository;
    private final ChatConversationRepository conversations;
    private final SenderTypeRepository types;
    private final PatientRepository patients;
    private final ProfessionalRepository professionals;

    public UpdateChatParticipantUseCase(ChatParticipantRepository repository, ChatConversationRepository conversations, SenderTypeRepository types, PatientRepository patients, ProfessionalRepository professionals) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.conversations = java.util.Objects.requireNonNull(conversations);
        this.types = java.util.Objects.requireNonNull(types);
        this.patients = java.util.Objects.requireNonNull(patients);
        this.professionals = java.util.Objects.requireNonNull(professionals);
    }
    public ChatParticipantResponse execute(UpdateChatParticipantCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new ChatParticipantNotFoundApplicationException(id));
        conversations.findById(command.conversationId()).orElseThrow(() -> new ChatConversationNotFoundApplicationException(command.conversationId()));
        types.findById(command.participantTypeId()).orElseThrow(() -> new SenderTypeNotFoundApplicationException(command.participantTypeId()));
        if (command.patientId() != null) { patients.findById(command.patientId()).orElseThrow(() -> new PatientNotFoundApplicationException(command.patientId())); }
        if (command.professionalId() != null) { professionals.findById(command.professionalId()).orElseThrow(() -> new ProfessionalNotFoundApplicationException(command.professionalId())); }
        aggregate.update(command.conversationId(), command.participantTypeId(), command.patientId(), command.professionalId());
        return ChatParticipantResponse.from(repository.save(aggregate));
    }
}

