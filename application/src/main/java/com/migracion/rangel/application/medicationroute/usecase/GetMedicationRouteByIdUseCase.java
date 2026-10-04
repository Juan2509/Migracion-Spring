package com.migracion.rangel.application.medicationroute.usecase;
import com.migracion.rangel.domain.medicationroute.port.repository.MedicationRouteRepository;
import com.migracion.rangel.domain.medicationroute.model.valueobject.MedicationRouteId;
import com.migracion.rangel.application.medicationroute.dto.MedicationRouteResponse;
import com.migracion.rangel.application.medicationroute.exception.MedicationRouteNotFoundApplicationException;
import com.migracion.rangel.application.medicationroute.exception.DuplicateMedicationRouteApplicationException;

public class GetMedicationRouteByIdUseCase {
    private final MedicationRouteRepository repository;
    public GetMedicationRouteByIdUseCase(MedicationRouteRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public MedicationRouteResponse execute(MedicationRouteId id) { return MedicationRouteResponse.from(repository.findById(id).orElseThrow(() -> new MedicationRouteNotFoundApplicationException(id))); }
}

