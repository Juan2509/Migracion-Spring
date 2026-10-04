package com.migracion.rangel.application.chatconversation.usecase;
import com.migracion.rangel.domain.conversationstatus.port.repository.ConversationStatusRepository;
import com.migracion.rangel.domain.priority.port.repository.PriorityRepository;
import com.migracion.rangel.application.conversationstatus.exception.ConversationStatusNotFoundApplicationException;
import com.migracion.rangel.application.priority.exception.PriorityNotFoundApplicationException;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.application.chatconversation.dto.ChatConversationResponse;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import com.migracion.rangel.application.chatconversation.command.RegisterChatConversationCommand;
import com.migracion.rangel.domain.chatconversation.model.aggregate.ChatConversation;
public class RegisterChatConversationUseCase {
    private final ChatConversationRepository repository;
    private final ConversationStatusRepository statuses;
    private final PriorityRepository priorities;
    public RegisterChatConversationUseCase(ChatConversationRepository repository, ConversationStatusRepository statuses, PriorityRepository priorities) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.statuses = java.util.Objects.requireNonNull(statuses);
        this.priorities = java.util.Objects.requireNonNull(priorities);
    }
    public ChatConversationResponse execute(RegisterChatConversationCommand command) {
        var aggregate = ChatConversation.register(command.conversationStatusId(), command.priorityId(), command.lastMessageAt(), command.closed(), command.closedAt(), command.closedBy());
        statuses.findById(command.conversationStatusId()).orElseThrow(() -> new ConversationStatusNotFoundApplicationException(command.conversationStatusId()));
        priorities.findById(command.priorityId()).orElseThrow(() -> new PriorityNotFoundApplicationException(command.priorityId()));
        return ChatConversationResponse.from(repository.save(aggregate));
    }
}

