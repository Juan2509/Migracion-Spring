package com.migracion.rangel.domain.chatmessage.model.aggregate;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.messagetype.model.valueobject.MessageTypeId;
import com.migracion.rangel.domain.chatparticipant.model.valueobject.ChatParticipantId;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.chatmessage.model.valueobject.ChatMessageId;
import com.migracion.rangel.domain.chatmessage.event.*;
public final class ChatMessage extends AggregateRoot {
    private final ChatMessageId id;
    private ChatConversationId conversationId;
    private MessageTypeId messageTypeId;
    private ChatParticipantId participantId;
    private String content;
    private String metadata;

    private final LocalDateTime createdAt;
    private ChatMessage(ChatMessageId id, ChatConversationId conversationId, MessageTypeId messageTypeId, ChatParticipantId participantId, String content, String metadata, LocalDateTime createdAt) {
        this.id = Objects.requireNonNull(id);
        this.createdAt = Objects.requireNonNull(createdAt);
        setDetails(conversationId, messageTypeId, participantId, content, metadata);
    }
    public static ChatMessage register(ChatConversationId conversationId, MessageTypeId messageTypeId, ChatParticipantId participantId, String content, String metadata) {
        var now = LocalDateTime.now();
        var aggregate = new ChatMessage(ChatMessageId.generate(), conversationId, messageTypeId, participantId, content, metadata, now);
        aggregate.recordEvent(new ChatMessageRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static ChatMessage restore(ChatMessageId id, ChatConversationId conversationId, MessageTypeId messageTypeId, ChatParticipantId participantId, String content, String metadata, LocalDateTime createdAt) {
        return new ChatMessage(id, conversationId, messageTypeId, participantId, content, metadata, createdAt);
    }
    public void update(ChatConversationId conversationId, MessageTypeId messageTypeId, ChatParticipantId participantId, String content, String metadata) {
        setDetails(conversationId, messageTypeId, participantId, content, metadata);
        recordEvent(new ChatMessageUpdatedEvent(id, LocalDateTime.now()));
    }
    private void setDetails(ChatConversationId conversationId, MessageTypeId messageTypeId, ChatParticipantId participantId, String content, String metadata) {
        Objects.requireNonNull(conversationId, "conversationId es obligatorio");
        Objects.requireNonNull(messageTypeId, "messageTypeId es obligatorio");
        Objects.requireNonNull(participantId, "participantId es obligatorio");
        Objects.requireNonNull(content, "content es obligatorio");
        Objects.requireNonNull(metadata, "metadata es obligatorio");
        this.conversationId = conversationId;
        this.messageTypeId = messageTypeId;
        this.participantId = participantId;
        this.content = content;
        this.metadata = metadata;
    }
    public ChatMessageId id() { return id; }
    public ChatConversationId conversationId() { return conversationId; }
    public MessageTypeId messageTypeId() { return messageTypeId; }
    public ChatParticipantId participantId() { return participantId; }
    public String content() { return content; }
    public String metadata() { return metadata; }
    public LocalDateTime createdAt() { return createdAt; }
}
