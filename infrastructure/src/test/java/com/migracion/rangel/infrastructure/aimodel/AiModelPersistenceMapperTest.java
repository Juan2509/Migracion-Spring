package com.migracion.rangel.infrastructure.aimodel;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.aimodel.model.aggregate.AiModel;
import com.migracion.rangel.application.aimodel.dto.AiModelResponse;
import com.migracion.rangel.infrastructure.aimodel.adapters.out.persistence.mappers.AiModelPersistenceMapper;
class AiModelPersistenceMapperTest {
    @Test
    void roundTripPreservesAllFieldsAndDecimalPrecisionWithoutEvents() {
        var original = AiModel.register("texto", "Modelo", "key", new BigDecimal("0.12345678"), new BigDecimal("9999.99999999"), 123, 456, true);
        var mapper = new AiModelPersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(original));
        assertEquals(AiModelResponse.from(original), AiModelResponse.from(restored));
        assertTrue(restored.domainEvents().isEmpty());
    }
}
