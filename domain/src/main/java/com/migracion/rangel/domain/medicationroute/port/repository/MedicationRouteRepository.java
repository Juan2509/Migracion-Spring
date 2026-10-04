package com.migracion.rangel.domain.medicationroute.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.medicationroute.model.aggregate.MedicationRoute;
import com.migracion.rangel.domain.medicationroute.model.valueobject.MedicationRouteId;
public interface MedicationRouteRepository {
    MedicationRoute save(MedicationRoute aggregate);
    Optional<MedicationRoute> findById(MedicationRouteId id);
    List<MedicationRoute> findAll();
    void delete(MedicationRoute aggregate);
    boolean existsByCode(String value);
    boolean existsByCodeAndIdNot(String value, MedicationRouteId id);
}

