package com.migracion.rangel.domain.encounterstatus.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.encounterstatus.model.aggregate.EncounterStatus;
import com.migracion.rangel.domain.encounterstatus.model.valueobject.EncounterStatusId;
public interface EncounterStatusRepository {
    EncounterStatus save(EncounterStatus aggregate);
    Optional<EncounterStatus> findById(EncounterStatusId id);
    List<EncounterStatus> findAll();
    void delete(EncounterStatus aggregate);
    boolean existsByCode(String value);
    boolean existsByCodeAndIdNot(String value, EncounterStatusId id);
    boolean existsByName(String value);
    boolean existsByNameAndIdNot(String value, EncounterStatusId id);
}

