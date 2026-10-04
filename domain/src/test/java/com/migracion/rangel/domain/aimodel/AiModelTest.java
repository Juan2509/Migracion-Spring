package com.migracion.rangel.domain.aimodel;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.aimodel.model.aggregate.AiModel;
import com.migracion.rangel.domain.aimodel.event.*;
class AiModelTest {
    @Test
    void auditIdentityAndEventsSurviveRestorationAndUpdate() {
        var original = AiModel.register("texto libre", "Modelo", "key", BigDecimal.ZERO, BigDecimal.ONE, 0, -1, true);
        assertInstanceOf(AiModelRegisteredEvent.class, original.domainEvents().getFirst());
        var restored = AiModel.restore(original.id(), original.providerModelId(), original.nameModel(), original.modelKey(),
                original.inputTokenPrice(), original.outputTokenPrice(), original.maxTokens(), original.contextWindow(),
                original.isActive(), original.createdAt(), original.updatedAt());
        assertTrue(restored.domainEvents().isEmpty());
        restored.update("otro", "Nuevo", "otra-key", new BigDecimal("-1.12345678"), BigDecimal.ZERO, -2, 0, false);
        assertEquals(original.id(), restored.id());
        assertEquals(original.createdAt(), restored.createdAt());
        assertEquals("otra-key", restored.modelKey());
        assertInstanceOf(AiModelUpdatedEvent.class, restored.domainEvents().getFirst());
    }
    @Test
    void decimalLimitsAreExactAndInvalidUpdateIsAtomic() {
        var original = AiModel.register("p", "Modelo", "key", new BigDecimal("9999.99999999"), new BigDecimal("-9999.99999999"), 1, 1, true);
        var updatedAt = original.updatedAt();
        for (var price : new BigDecimal[]{new BigDecimal("10000"), new BigDecimal("0.000000001")}) {
            assertThrows(IllegalArgumentException.class,
                    () -> original.update("cambio", "Cambio", "otra", BigDecimal.ZERO, price, 2, 2, false));
            assertEquals("p", original.providerModelId());
            assertEquals(new BigDecimal("9999.99999999"), original.inputTokenPrice());
            assertEquals(updatedAt, original.updatedAt());
            assertEquals(1, original.domainEvents().size());
        }
    }
    @Test
    void stringLimitsAndRequiredFieldsFollowSchema() {
        assertThrows(IllegalArgumentException.class, () -> AiModel.register("x".repeat(51), "n", "k", BigDecimal.ZERO, BigDecimal.ZERO, 1, 1, true));
        assertThrows(IllegalArgumentException.class, () -> AiModel.register("p", "x".repeat(101), "k", BigDecimal.ZERO, BigDecimal.ZERO, 1, 1, true));
        assertThrows(IllegalArgumentException.class, () -> AiModel.register("p", "n", "x".repeat(121), BigDecimal.ZERO, BigDecimal.ZERO, 1, 1, true));
        assertThrows(NullPointerException.class, () -> AiModel.register("p", "n", "k", null, BigDecimal.ZERO, 1, 1, true));
        assertThrows(NullPointerException.class, () -> AiModel.register("p", "n", "k", BigDecimal.ZERO, BigDecimal.ZERO, null, 1, true));
    }
}
