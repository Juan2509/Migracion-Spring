package com.migracion.rangel.application.treatmentstatus.usecase;
import com.migracion.rangel.domain.treatmentstatus.port.repository.TreatmentStatusRepository;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.migracion.rangel.application.treatmentstatus.dto.TreatmentStatusResponse;
import com.migracion.rangel.application.treatmentstatus.exception.TreatmentStatusNotFoundApplicationException;
import com.migracion.rangel.application.treatmentstatus.exception.DuplicateTreatmentStatusApplicationException;
import java.util.List;
public class ListTreatmentStatusUseCase {
    private final TreatmentStatusRepository repository;
    public ListTreatmentStatusUseCase(TreatmentStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<TreatmentStatusResponse> execute() { return repository.findAll().stream().map(TreatmentStatusResponse::from).toList(); }
}


