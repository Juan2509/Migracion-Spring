package com.migracion.rangel.infrastructure.risklevel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.migracion.rangel.domain.risklevel.model.aggregate.RiskLevel;
import com.migracion.rangel.application.risklevel.dto.RiskLevelResponse;
import com.migracion.rangel.infrastructure.risklevel.adapters.out.persistence.mappers.RiskLevelPersistenceMapper;
class RiskLevelPersistenceMapperTest {
    @Test void roundTripPreservesSeverityAuditAndFalseWithoutEvents() {
        var original = RiskLevel.register("LOW", "Nivel", false, -1); var mapper = new RiskLevelPersistenceMapper();
        var restored = mapper.toDomain(mapper.toJpa(original)); assertEquals(RiskLevelResponse.from(original), RiskLevelResponse.from(restored)); assertTrue(restored.domainEvents().isEmpty());
    }
}
