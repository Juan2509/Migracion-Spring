package com.migracion.rangel.domain.risklevel.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.risklevel.model.aggregate.RiskLevel;
import com.migracion.rangel.domain.risklevel.model.valueobject.RiskLevelId;
public interface RiskLevelRepository {
    RiskLevel save(RiskLevel aggregate);
    Optional<RiskLevel> findById(RiskLevelId id);
    List<RiskLevel> findAll();
    void delete(RiskLevel aggregate);
    boolean existsByCode(String value);
    boolean existsByCodeAndIdNot(String value, RiskLevelId id);
}

