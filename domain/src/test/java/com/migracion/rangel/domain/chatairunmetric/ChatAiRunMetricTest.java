package com.migracion.rangel.domain.chatairunmetric;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.chatairunmetric.model.aggregate.ChatAiRunMetric;
import com.migracion.rangel.domain.chatairunmetric.event.*;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
class ChatAiRunMetricTest {
    @Test
    void restoreAndUpdatePreserveCreationDateWithoutCalculatingTotals() {
        var run = ChatAiRunId.generate();
        var original = ChatAiRunMetric.register(run,10,20,99,new BigDecimal("0.123456"));
        assertInstanceOf(ChatAiRunMetricRegisteredEvent.class,original.domainEvents().getFirst());
        var created = LocalDateTime.of(2020,1,1,0,0);
        var restored = ChatAiRunMetric.restore(original.id(),run,10,20,99,original.cost(),created);
        assertTrue(restored.domainEvents().isEmpty());
        restored.update(run,-1,0,-2,new BigDecimal("-9999.999999"));
        assertEquals(original.id(),restored.id());
        assertEquals(created,restored.createdAt());
        assertEquals(-2,restored.totalTokens());
        assertEquals(new BigDecimal("-9999.999999"),restored.cost());
        assertInstanceOf(ChatAiRunMetricUpdatedEvent.class,restored.domainEvents().getFirst());
    }
    @Test
    void decimalBoundariesAndInvalidUpdatePreserveState() {
        var run = ChatAiRunId.generate();
        var metric = ChatAiRunMetric.register(run,1,2,3,new BigDecimal("9999.999999"));
        for (var cost : new BigDecimal[]{new BigDecimal("10000"),new BigDecimal("0.0000001")}) {
            assertThrows(IllegalArgumentException.class, () -> metric.update(ChatAiRunId.generate(),4,5,6,cost));
            assertEquals(run,metric.aiRunId());
            assertEquals(1,metric.promptTokens());
            assertEquals(new BigDecimal("9999.999999"),metric.cost());
            assertEquals(1,metric.domainEvents().size());
        }
    }
    @Test
    void allFieldsAreRequired() {
        var run = ChatAiRunId.generate();
        assertThrows(NullPointerException.class, () -> ChatAiRunMetric.register(null,1,2,3,BigDecimal.ZERO));
        assertThrows(NullPointerException.class, () -> ChatAiRunMetric.register(run,null,2,3,BigDecimal.ZERO));
        assertThrows(NullPointerException.class, () -> ChatAiRunMetric.register(run,1,null,3,BigDecimal.ZERO));
        assertThrows(NullPointerException.class, () -> ChatAiRunMetric.register(run,1,2,null,BigDecimal.ZERO));
        assertThrows(NullPointerException.class, () -> ChatAiRunMetric.register(run,1,2,3,null));
    }
}
