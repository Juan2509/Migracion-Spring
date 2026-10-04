package com.migracion.rangel.application.medicationroute.usecase;
import com.migracion.rangel.domain.medicationroute.port.repository.MedicationRouteRepository;
import com.migracion.rangel.domain.medicationroute.model.valueobject.MedicationRouteId;
import com.migracion.rangel.application.medicationroute.dto.MedicationRouteResponse;
import com.migracion.rangel.application.medicationroute.exception.MedicationRouteNotFoundApplicationException;
import com.migracion.rangel.application.medicationroute.exception.DuplicateMedicationRouteApplicationException;
import com.migracion.rangel.application.medicationroute.command.UpdateMedicationRouteCommand;
public class UpdateMedicationRouteUseCase {
    private final MedicationRouteRepository repository;
    public UpdateMedicationRouteUseCase(MedicationRouteRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public MedicationRouteResponse execute(UpdateMedicationRouteCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new MedicationRouteNotFoundApplicationException(id));
        if (repository.existsByCodeAndIdNot(command.code(), id)) { throw new DuplicateMedicationRouteApplicationException(); }
        aggregate.update(command.code(), command.name(), command.active());
        return MedicationRouteResponse.from(repository.save(aggregate));
    }
}

