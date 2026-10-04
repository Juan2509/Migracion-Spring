package com.migracion.rangel.application.medicationroute.usecase;
import com.migracion.rangel.domain.medicationroute.port.repository.MedicationRouteRepository;
import com.migracion.rangel.domain.medicationroute.model.valueobject.MedicationRouteId;
import com.migracion.rangel.application.medicationroute.dto.MedicationRouteResponse;
import com.migracion.rangel.application.medicationroute.exception.MedicationRouteNotFoundApplicationException;
import com.migracion.rangel.application.medicationroute.exception.DuplicateMedicationRouteApplicationException;
import com.migracion.rangel.application.medicationroute.command.RegisterMedicationRouteCommand;
import com.migracion.rangel.domain.medicationroute.model.aggregate.MedicationRoute;
public class RegisterMedicationRouteUseCase {
    private final MedicationRouteRepository repository;
    public RegisterMedicationRouteUseCase(MedicationRouteRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public MedicationRouteResponse execute(RegisterMedicationRouteCommand command) {
        var aggregate = MedicationRoute.register(command.code(), command.name(), command.active());
        if (repository.existsByCode(command.code())) { throw new DuplicateMedicationRouteApplicationException(); }
        return MedicationRouteResponse.from(repository.save(aggregate));
    }
}

