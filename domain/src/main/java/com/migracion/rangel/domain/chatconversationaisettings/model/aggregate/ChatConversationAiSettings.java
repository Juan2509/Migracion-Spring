package com.migracion.rangel.domain.chatconversationaisettings.model.aggregate;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatconversation.model.valueobject.ChatConversationId;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.chatconversationaisettings.model.valueobject.ChatConversationAiSettingsId;
import com.migracion.rangel.domain.chatconversationaisettings.event.*;
public final class ChatConversationAiSettings extends AggregateRoot {
    private final ChatConversationAiSettingsId id;
    private ChatConversationId conversationId;
    private Boolean aiEnabled;
    private AiModelId defaultModelId;

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ChatConversationAiSettings(ChatConversationAiSettingsId id, ChatConversationId conversationId, Boolean aiEnabled, AiModelId defaultModelId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
        setDetails(conversationId, aiEnabled, defaultModelId);
    }
    public static ChatConversationAiSettings register(ChatConversationId conversationId, Boolean aiEnabled, AiModelId defaultModelId) {
        var now = LocalDateTime.now();
        var aggregate = new ChatConversationAiSettings(ChatConversationAiSettingsId.generate(), conversationId, aiEnabled, defaultModelId, now, now);
        aggregate.recordEvent(new ChatConversationAiSettingsRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static ChatConversationAiSettings restore(ChatConversationAiSettingsId id, ChatConversationId conversationId, Boolean aiEnabled, AiModelId defaultModelId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new ChatConversationAiSettings(id, conversationId, aiEnabled, defaultModelId, createdAt, updatedAt);
    }
    public void update(ChatConversationId conversationId, Boolean aiEnabled, AiModelId defaultModelId) {
        setDetails(conversationId, aiEnabled, defaultModelId);
        updatedAt = LocalDateTime.now();
        recordEvent(new ChatConversationAiSettingsUpdatedEvent(id, updatedAt));
    }
    private void setDetails(ChatConversationId conversationId, Boolean aiEnabled, AiModelId defaultModelId) {
        Objects.requireNonNull(conversationId, "conversationId es obligatorio");
        Objects.requireNonNull(aiEnabled, "aiEnabled es obligatorio");
        Objects.requireNonNull(defaultModelId, "defaultModelId es obligatorio");
        this.conversationId = conversationId;
        this.aiEnabled = aiEnabled;
        this.defaultModelId = defaultModelId;
    }
    public ChatConversationAiSettingsId id() { return id; }
    public ChatConversationId conversationId() { return conversationId; }
    public Boolean aiEnabled() { return aiEnabled; }
    public AiModelId defaultModelId() { return defaultModelId; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}
