package com.migracion.rangel.domain.encountermodality.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.encountermodality.model.aggregate.EncounterModality;
import com.migracion.rangel.domain.encountermodality.model.valueobject.EncounterModalityId;
public interface EncounterModalityRepository {
    EncounterModality save(EncounterModality aggregate);
    Optional<EncounterModality> findById(EncounterModalityId id);
    List<EncounterModality> findAll();
    void delete(EncounterModality aggregate);
    boolean existsByCode(String value);
    boolean existsByCodeAndIdNot(String value, EncounterModalityId id);
    boolean existsByName(String value);
    boolean existsByNameAndIdNot(String value, EncounterModalityId id);
}

