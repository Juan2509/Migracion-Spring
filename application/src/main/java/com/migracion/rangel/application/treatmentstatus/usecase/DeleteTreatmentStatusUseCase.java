package com.migracion.rangel.application.treatmentstatus.usecase;
import com.migracion.rangel.domain.treatmentstatus.port.repository.TreatmentStatusRepository;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.migracion.rangel.application.treatmentstatus.dto.TreatmentStatusResponse;
import com.migracion.rangel.application.treatmentstatus.exception.TreatmentStatusNotFoundApplicationException;
import com.migracion.rangel.application.treatmentstatus.exception.DuplicateTreatmentStatusApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.treatmentstatus.event.TreatmentStatusDeletedEvent;
public class DeleteTreatmentStatusUseCase {
    private final TreatmentStatusRepository repository;
    public DeleteTreatmentStatusUseCase(TreatmentStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public TreatmentStatusDeletedEvent execute(TreatmentStatusId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new TreatmentStatusNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new TreatmentStatusDeletedEvent(id, LocalDateTime.now());
    }
}


