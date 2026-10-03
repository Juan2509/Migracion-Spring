package com.migracion.rangel.domain.encounter.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.encounter.model.aggregate.Encounter;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
public interface EncounterRepository {
    Encounter save(Encounter aggregate);
    Optional<Encounter> findById(EncounterId id);
    List<Encounter> findAll();
    void delete(Encounter aggregate);
}

