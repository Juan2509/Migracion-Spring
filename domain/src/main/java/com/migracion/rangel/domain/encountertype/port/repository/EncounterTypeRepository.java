package com.migracion.rangel.domain.encountertype.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.encountertype.model.aggregate.EncounterType;
import com.migracion.rangel.domain.encountertype.model.valueobject.EncounterTypeId;
public interface EncounterTypeRepository {
    EncounterType save(EncounterType aggregate);
    Optional<EncounterType> findById(EncounterTypeId id);
    List<EncounterType> findAll();
    void delete(EncounterType aggregate);
    boolean existsByCode(String value);
    boolean existsByCodeAndIdNot(String value, EncounterTypeId id);
    boolean existsByName(String value);
    boolean existsByNameAndIdNot(String value, EncounterTypeId id);
}

