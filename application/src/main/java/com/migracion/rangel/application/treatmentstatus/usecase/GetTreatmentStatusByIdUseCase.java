package com.migracion.rangel.application.treatmentstatus.usecase;
import com.migracion.rangel.domain.treatmentstatus.port.repository.TreatmentStatusRepository;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.migracion.rangel.application.treatmentstatus.dto.TreatmentStatusResponse;
import com.migracion.rangel.application.treatmentstatus.exception.TreatmentStatusNotFoundApplicationException;
import com.migracion.rangel.application.treatmentstatus.exception.DuplicateTreatmentStatusApplicationException;

public class GetTreatmentStatusByIdUseCase {
    private final TreatmentStatusRepository repository;
    public GetTreatmentStatusByIdUseCase(TreatmentStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public TreatmentStatusResponse execute(TreatmentStatusId id) { return TreatmentStatusResponse.from(repository.findById(id).orElseThrow(() -> new TreatmentStatusNotFoundApplicationException(id))); }
}


