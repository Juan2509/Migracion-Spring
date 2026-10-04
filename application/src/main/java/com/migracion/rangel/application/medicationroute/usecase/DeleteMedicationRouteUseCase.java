package com.migracion.rangel.application.medicationroute.usecase;
import com.migracion.rangel.domain.medicationroute.port.repository.MedicationRouteRepository;
import com.migracion.rangel.domain.medicationroute.model.valueobject.MedicationRouteId;
import com.migracion.rangel.application.medicationroute.dto.MedicationRouteResponse;
import com.migracion.rangel.application.medicationroute.exception.MedicationRouteNotFoundApplicationException;
import com.migracion.rangel.application.medicationroute.exception.DuplicateMedicationRouteApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.medicationroute.event.MedicationRouteDeletedEvent;
public class DeleteMedicationRouteUseCase {
    private final MedicationRouteRepository repository;
    public DeleteMedicationRouteUseCase(MedicationRouteRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public MedicationRouteDeletedEvent execute(MedicationRouteId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new MedicationRouteNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new MedicationRouteDeletedEvent(id, LocalDateTime.now());
    }
}

