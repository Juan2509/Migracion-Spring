package com.migracion.rangel.domain.stateregion.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.stateregion.model.aggregate.StateRegion;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;
public interface StateRegionRepository {
    StateRegion save(StateRegion region);
    Optional<StateRegion> findById(StateRegionId id);
    List<StateRegion> findAll();
    void delete(StateRegion region);
}
