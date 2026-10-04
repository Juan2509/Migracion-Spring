package com.migracion.rangel.domain.aimodel.model.aggregate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import com.migracion.rangel.domain.common.model.AggregateRoot;
import com.migracion.rangel.domain.aimodel.model.valueobject.AiModelId;
import com.migracion.rangel.domain.aimodel.event.*;
public final class AiModel extends AggregateRoot {
    private final AiModelId id;
    private String providerModelId;
    private String nameModel;
    private String modelKey;
    private BigDecimal inputTokenPrice;
    private BigDecimal outputTokenPrice;
    private Integer maxTokens;
    private Integer contextWindow;
    private Boolean isActive;

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private AiModel(AiModelId id, String providerModelId, String nameModel, String modelKey, BigDecimal inputTokenPrice, BigDecimal outputTokenPrice, Integer maxTokens, Integer contextWindow, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
        setDetails(providerModelId, nameModel, modelKey, inputTokenPrice, outputTokenPrice, maxTokens, contextWindow, isActive);
    }
    public static AiModel register(String providerModelId, String nameModel, String modelKey, BigDecimal inputTokenPrice, BigDecimal outputTokenPrice, Integer maxTokens, Integer contextWindow, Boolean isActive) {
        var now = LocalDateTime.now();
        var aggregate = new AiModel(AiModelId.generate(), providerModelId, nameModel, modelKey, inputTokenPrice, outputTokenPrice, maxTokens, contextWindow, isActive, now, now);
        aggregate.recordEvent(new AiModelRegisteredEvent(aggregate.id, now));
        return aggregate;
    }
    public static AiModel restore(AiModelId id, String providerModelId, String nameModel, String modelKey, BigDecimal inputTokenPrice, BigDecimal outputTokenPrice, Integer maxTokens, Integer contextWindow, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new AiModel(id, providerModelId, nameModel, modelKey, inputTokenPrice, outputTokenPrice, maxTokens, contextWindow, isActive, createdAt, updatedAt);
    }
    public void update(String providerModelId, String nameModel, String modelKey, BigDecimal inputTokenPrice, BigDecimal outputTokenPrice, Integer maxTokens, Integer contextWindow, Boolean isActive) {
        setDetails(providerModelId, nameModel, modelKey, inputTokenPrice, outputTokenPrice, maxTokens, contextWindow, isActive);
        updatedAt = LocalDateTime.now();
        recordEvent(new AiModelUpdatedEvent(id, updatedAt));
    }
    private void setDetails(String providerModelId, String nameModel, String modelKey, BigDecimal inputTokenPrice, BigDecimal outputTokenPrice, Integer maxTokens, Integer contextWindow, Boolean isActive) {
        validateText(providerModelId, 50, "providerModelId");
        validateText(nameModel, 100, "nameModel");
        validateText(modelKey, 120, "modelKey");
        validatePrice(inputTokenPrice, "inputTokenPrice");
        validatePrice(outputTokenPrice, "outputTokenPrice");
        Objects.requireNonNull(maxTokens, "maxTokens es obligatorio");
        Objects.requireNonNull(contextWindow, "contextWindow es obligatorio");
        Objects.requireNonNull(isActive, "isActive es obligatorio");
        this.providerModelId = providerModelId;
        this.nameModel = nameModel;
        this.modelKey = modelKey;
        this.inputTokenPrice = inputTokenPrice;
        this.outputTokenPrice = outputTokenPrice;
        this.maxTokens = maxTokens;
        this.contextWindow = contextWindow;
        this.isActive = isActive;
    }
    private static void validateText(String value, int limit, String field) {
        Objects.requireNonNull(value, field + " es obligatorio");
        if (value.codePointCount(0, value.length()) > limit) {
            throw new IllegalArgumentException(field + " supera " + limit + " caracteres");
        }
    }
    private static void validatePrice(BigDecimal value, String field) {
        Objects.requireNonNull(value, field + " es obligatorio");
        // Se acepta cualquier valor representable exactamente como DECIMAL(12,8).
        final BigDecimal scaled;
        try {
            scaled = value.setScale(8, RoundingMode.UNNECESSARY);
        } catch (ArithmeticException exception) {
            throw new IllegalArgumentException(field + " requiere como máximo 8 decimales significativos", exception);
        }
        if (scaled.precision() > 12) {
            throw new IllegalArgumentException(field + " supera DECIMAL(12,8)");
        }
    }
    public AiModelId id() { return id; }
    public String providerModelId() { return providerModelId; }
    public String nameModel() { return nameModel; }
    public String modelKey() { return modelKey; }
    public BigDecimal inputTokenPrice() { return inputTokenPrice; }
    public BigDecimal outputTokenPrice() { return outputTokenPrice; }
    public Integer maxTokens() { return maxTokens; }
    public Integer contextWindow() { return contextWindow; }
    public Boolean isActive() { return isActive; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}
