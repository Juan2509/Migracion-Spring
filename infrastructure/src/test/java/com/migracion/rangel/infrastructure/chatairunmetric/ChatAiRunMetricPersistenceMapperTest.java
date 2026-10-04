package com.migracion.rangel.infrastructure.chatairunmetric;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.chatairunmetric.model.aggregate.ChatAiRunMetric;
import com.migracion.rangel.domain.chatairun.model.valueobject.ChatAiRunId;
import com.migracion.rangel.application.chatairunmetric.dto.ChatAiRunMetricResponse;
import com.migracion.rangel.infrastructure.chatairunmetric.adapters.out.persistence.mappers.ChatAiRunMetricPersistenceMapper;
class ChatAiRunMetricPersistenceMapperTest {
    @Test
    void roundTripPreservesAllFieldsAndCostWithoutEvents() {
        var original = ChatAiRunMetric.register(ChatAiRunId.generate(),1,2,99,new BigDecimal("9999.999999"));
        var mapper = new ChatAiRunMetricPersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(original));
        assertEquals(ChatAiRunMetricResponse.from(original),ChatAiRunMetricResponse.from(restored));
        assertTrue(restored.domainEvents().isEmpty());
    }
}
