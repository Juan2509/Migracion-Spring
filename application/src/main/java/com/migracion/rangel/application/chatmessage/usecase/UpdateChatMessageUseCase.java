package com.migracion.rangel.application.chatmessage.usecase;
import com.migracion.rangel.domain.chatconversation.port.repository.ChatConversationRepository;
import com.migracion.rangel.application.chatconversation.exception.ChatConversationNotFoundApplicationException;
import com.migracion.rangel.domain.messagetype.port.repository.MessageTypeRepository;
import com.migracion.rangel.application.messagetype.exception.MessageTypeNotFoundApplicationException;
import com.migracion.rangel.domain.chatparticipant.port.repository.ChatParticipantRepository;
import com.migracion.rangel.application.chatparticipant.exception.ChatParticipantNotFoundApplicationException;
import com.migracion.rangel.domain.chatmessage.port.repository.ChatMessageRepository;
import com.migracion.rangel.domain.chatmessage.model.valueobject.ChatMessageId;
import com.migracion.rangel.application.chatmessage.dto.ChatMessageResponse;
import com.migracion.rangel.application.chatmessage.exception.ChatMessageNotFoundApplicationException;
import com.migracion.rangel.application.chatmessage.command.UpdateChatMessageCommand;
public class UpdateChatMessageUseCase {
    private final ChatMessageRepository repository;
    private final ChatConversationRepository conversations;
    private final MessageTypeRepository types;
    private final ChatParticipantRepository participants;

    public UpdateChatMessageUseCase(ChatMessageRepository repository, ChatConversationRepository conversations, MessageTypeRepository types, ChatParticipantRepository participants) {
        this.repository = java.util.Objects.requireNonNull(repository);
        this.conversations = java.util.Objects.requireNonNull(conversations);
        this.types = java.util.Objects.requireNonNull(types);
        this.participants = java.util.Objects.requireNonNull(participants);
    }
    public ChatMessageResponse execute(UpdateChatMessageCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new ChatMessageNotFoundApplicationException(id));
        conversations.findById(command.conversationId()).orElseThrow(() -> new ChatConversationNotFoundApplicationException(command.conversationId()));
        types.findById(command.messageTypeId()).orElseThrow(() -> new MessageTypeNotFoundApplicationException(command.messageTypeId()));
        participants.findById(command.participantId()).orElseThrow(() -> new ChatParticipantNotFoundApplicationException(command.participantId()));
        aggregate.update(command.conversationId(), command.messageTypeId(), command.participantId(), command.content(), command.metadata());
        return ChatMessageResponse.from(repository.save(aggregate));
    }
}

