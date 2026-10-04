package com.migracion.rangel.domain.priority.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.priority.model.aggregate.Priority;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
public interface PriorityRepository {
    Priority save(Priority aggregate);
    Optional<Priority> findById(PriorityId id);
    List<Priority> findAll();
    void delete(Priority aggregate);
}
