package com.migracion.rangel.domain.airunstatus.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.airunstatus.model.aggregate.AiRunStatus;
import com.migracion.rangel.domain.airunstatus.model.valueobject.AiRunStatusId;
public interface AiRunStatusRepository {
    AiRunStatus save(AiRunStatus aggregate);
    Optional<AiRunStatus> findById(AiRunStatusId id);
    List<AiRunStatus> findAll();
    void delete(AiRunStatus aggregate);
}
