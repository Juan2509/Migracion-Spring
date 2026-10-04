package com.migracion.rangel.infrastructure.chatairunmetric.adapters.out.persistence.entity;
import java.math.BigDecimal;
import java.util.UUID;
import java.time.LocalDateTime;
import jakarta.persistence.*;
@Entity
@Table(name = "chat_ai_run_metrics")
public class ChatAiRunMetricJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "ai_run_id", nullable = false)
    private UUID aiRunId;
    @Column(name = "prompt_tokens", nullable = false)
    private Integer promptTokens;
    @Column(name = "completion_tokens", nullable = false)
    private Integer completionTokens;
    @Column(name = "total_tokens", nullable = false)
    private Integer totalTokens;
    @Column(name = "cost", nullable = false, precision = 10, scale = 6)
    private BigDecimal cost;
    public BigDecimal getCost() { return cost; }
    public void setCost(BigDecimal cost) { this.cost = cost; }
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    public ChatAiRunMetricJpaEntity() {}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getAiRunId() { return aiRunId; }
    public void setAiRunId(UUID aiRunId) { this.aiRunId = aiRunId; }
    public Integer getPromptTokens() { return promptTokens; }
    public void setPromptTokens(Integer promptTokens) { this.promptTokens = promptTokens; }
    public Integer getCompletionTokens() { return completionTokens; }
    public void setCompletionTokens(Integer completionTokens) { this.completionTokens = completionTokens; }
    public Integer getTotalTokens() { return totalTokens; }
    public void setTotalTokens(Integer totalTokens) { this.totalTokens = totalTokens; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
