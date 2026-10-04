package com.migracion.rangel.application.medicationroute.usecase;
import com.migracion.rangel.domain.medicationroute.port.repository.MedicationRouteRepository;
import com.migracion.rangel.domain.medicationroute.model.valueobject.MedicationRouteId;
import com.migracion.rangel.application.medicationroute.dto.MedicationRouteResponse;
import com.migracion.rangel.application.medicationroute.exception.MedicationRouteNotFoundApplicationException;
import com.migracion.rangel.application.medicationroute.exception.DuplicateMedicationRouteApplicationException;
import java.util.List;
public class ListMedicationRouteUseCase {
    private final MedicationRouteRepository repository;
    public ListMedicationRouteUseCase(MedicationRouteRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<MedicationRouteResponse> execute() { return repository.findAll().stream().map(MedicationRouteResponse::from).toList(); }
}

