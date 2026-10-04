package com.migracion.rangel.domain.chatairunmetric.model.aggregate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.chatairunmetric.model.valueobject.ChatAiRunMetricId;
import com.migracion.rangel.domain.chatairunmetric.event.*;
public final class ChatAiRunMetric extends AggregateRoot {
    private final ChatAiRunMetricId id;
    private ChatAiRunId aiRunId;
    private Integer promptTokens;
    private Integer completionTokens;
    private Integer totalTokens;
    private BigDecimal cost;

    private final LocalDateTime createdAt;
    private ChatAiRunMetric(ChatAiRunMetricId id, ChatAiRunId aiRunId, Integer promptTokens, Integer completionTokens, Integer totalTokens, BigDecimal cost, LocalDateTime createdAt) {
        this.id = Objects.requireNonNull(id);
        this.createdAt = Objects.requireNonNull(createdAt);
        setDetails(aiRunId, promptTokens, completionTokens, totalTokens, cost);
    }
    public static ChatAiRunMetric register(ChatAiRunId aiRunId, Integer promptTokens, Integer completionTokens, Integer totalTokens, BigDecimal cost) {
        var now = LocalDateTime.now();
        var aggregate = new ChatAiRunMetric(ChatAiRunMetricId.generate(), aiRunId, promptTokens, completionTokens, totalTokens, cost, now);
        aggregate.recordEvent(new ChatAiRunMetricRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static ChatAiRunMetric restore(ChatAiRunMetricId id, ChatAiRunId aiRunId, Integer promptTokens, Integer completionTokens, Integer totalTokens, BigDecimal cost, LocalDateTime createdAt) {
        return new ChatAiRunMetric(id, aiRunId, promptTokens, completionTokens, totalTokens, cost, createdAt);
    }
    public void update(ChatAiRunId aiRunId, Integer promptTokens, Integer completionTokens, Integer totalTokens, BigDecimal cost) {
        setDetails(aiRunId, promptTokens, completionTokens, totalTokens, cost);
        recordEvent(new ChatAiRunMetricUpdatedEvent(id, LocalDateTime.now()));
    }
    private void setDetails(ChatAiRunId aiRunId, Integer promptTokens, Integer completionTokens, Integer totalTokens, BigDecimal cost) {
        Objects.requireNonNull(aiRunId, "aiRunId es obligatorio");
        Objects.requireNonNull(promptTokens, "promptTokens es obligatorio");
        Objects.requireNonNull(completionTokens, "completionTokens es obligatorio");
        Objects.requireNonNull(totalTokens, "totalTokens es obligatorio");
        validateCost(cost);
        this.aiRunId = aiRunId;
        this.promptTokens = promptTokens;
        this.completionTokens = completionTokens;
        this.totalTokens = totalTokens;
        this.cost = cost;

    }
    private static void validateCost(BigDecimal value) {
        Objects.requireNonNull(value, "cost es obligatorio");
        final BigDecimal scaled;
        try {
            scaled = value.setScale(6, java.math.RoundingMode.UNNECESSARY);
        } catch (ArithmeticException exception) {
            throw new IllegalArgumentException("cost requiere como máximo 6 decimales significativos", exception);
        }
        if (scaled.precision() > 10) { throw new IllegalArgumentException("cost supera DECIMAL(10,6)"); }
    }
    public BigDecimal cost() { return cost; }
    public ChatAiRunMetricId id() { return id; }
    public ChatAiRunId aiRunId() { return aiRunId; }
    public Integer promptTokens() { return promptTokens; }
    public Integer completionTokens() { return completionTokens; }
    public Integer totalTokens() { return totalTokens; }
    public LocalDateTime createdAt() { return createdAt; }
}
