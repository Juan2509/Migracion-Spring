package com.migracion.rangel.application.chatescalation.usecase;
import com.migracion.rangel.domain.escalationstatus.port.repository.EscalationStatusRepository;
import com.migracion.rangel.application.escalationstatus.exception.EscalationStatusNotFoundApplicationException;
import com.migracion.rangel.domain.escalationstatus.model.valueobject.EscalationStatusId;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import com.migracion.rangel.domain.chatescalation.port.repository.ChatEscalationRepository;
import com.migracion.rangel.domain.chatescalation.model.valueobject.ChatEscalationId;
import com.migracion.rangel.application.chatescalation.dto.ChatEscalationResponse;
import com.migracion.rangel.application.chatescalation.exception.ChatEscalationNotFoundApplicationException;
import com.migracion.rangel.application.chatescalation.command.RegisterChatEscalationCommand;
import com.migracion.rangel.domain.chatescalation.model.aggregate.ChatEscalation;
public class RegisterChatEscalationUseCase {
    private final ChatEscalationRepository repository;
    private final ChatConversationRepository conversations;
    private final EscalationStatusRepository statuses;
    public RegisterChatEscalationUseCase(ChatEscalationRepository repository, ChatConversationRepository conversations, EscalationStatusRepository statuses) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.conversations = java.util.Objects.requireNonNull(conversations);
        this.statuses = java.util.Objects.requireNonNull(statuses);
    }
    public ChatEscalationResponse execute(RegisterChatEscalationCommand command) {
        var aggregate = ChatEscalation.register(command.conversationId(), command.reason(), command.statusId(), command.fromAi());
        conversations.findById(command.conversationId()).orElseThrow(() -> new ChatConversationNotFoundApplicationException(command.conversationId()));
        statuses.findById(command.statusId()).orElseThrow(() -> new EscalationStatusNotFoundApplicationException(command.statusId()));
        return ChatEscalationResponse.from(repository.save(aggregate));
    }
}

