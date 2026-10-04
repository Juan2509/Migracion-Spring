package com.migracion.rangel.domain.chatairunerror.model.aggregate;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.chatairunerror.model.valueobject.ChatAiRunErrorId;
import com.migracion.rangel.domain.chatairunerror.event.*;
public final class ChatAiRunError extends AggregateRoot {
    private final ChatAiRunErrorId id;
    private ChatAiRunId aiRunId;
    private String errorMessage;
    private String errorCode;
    private String providerErrorId;

    private final LocalDateTime createdAt;
    private ChatAiRunError(ChatAiRunErrorId id, ChatAiRunId aiRunId, String errorMessage, String errorCode, String providerErrorId, LocalDateTime createdAt) {
        this.id = Objects.requireNonNull(id);
        this.createdAt = Objects.requireNonNull(createdAt);
        setDetails(aiRunId, errorMessage, errorCode, providerErrorId);
    }
    public static ChatAiRunError register(ChatAiRunId aiRunId, String errorMessage, String errorCode, String providerErrorId) {
        var now = LocalDateTime.now();
        var aggregate = new ChatAiRunError(ChatAiRunErrorId.generate(), aiRunId, errorMessage, errorCode, providerErrorId, now);
        aggregate.recordEvent(new ChatAiRunErrorRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static ChatAiRunError restore(ChatAiRunErrorId id, ChatAiRunId aiRunId, String errorMessage, String errorCode, String providerErrorId, LocalDateTime createdAt) {
        return new ChatAiRunError(id, aiRunId, errorMessage, errorCode, providerErrorId, createdAt);
    }
    public void update(ChatAiRunId aiRunId, String errorMessage, String errorCode, String providerErrorId) {
        setDetails(aiRunId, errorMessage, errorCode, providerErrorId);
        recordEvent(new ChatAiRunErrorUpdatedEvent(id, LocalDateTime.now()));
    }
    private void setDetails(ChatAiRunId aiRunId, String errorMessage, String errorCode, String providerErrorId) {
        Objects.requireNonNull(aiRunId, "aiRunId es obligatorio");
        Objects.requireNonNull(errorMessage, "errorMessage es obligatorio");
        validateText(errorCode, 80, "errorCode");
        validateText(providerErrorId, 120, "providerErrorId");
        this.aiRunId = aiRunId;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.providerErrorId = providerErrorId;

    }
    private static void validateText(String value, int limit, String field) {
        Objects.requireNonNull(value, field + " es obligatorio");
        if (value.codePointCount(0,value.length()) > limit) {
            throw new IllegalArgumentException(field + " supera " + limit + " caracteres");
        }
    }
    public ChatAiRunErrorId id() { return id; }
    public ChatAiRunId aiRunId() { return aiRunId; }
    public String errorMessage() { return errorMessage; }
    public String errorCode() { return errorCode; }
    public String providerErrorId() { return providerErrorId; }
    public LocalDateTime createdAt() { return createdAt; }
}
