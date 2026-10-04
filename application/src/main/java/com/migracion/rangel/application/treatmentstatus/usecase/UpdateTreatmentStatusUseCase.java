package com.migracion.rangel.application.treatmentstatus.usecase;
import com.migracion.rangel.domain.treatmentstatus.port.repository.TreatmentStatusRepository;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.migracion.rangel.application.treatmentstatus.dto.TreatmentStatusResponse;
import com.migracion.rangel.application.treatmentstatus.exception.TreatmentStatusNotFoundApplicationException;
import com.migracion.rangel.application.treatmentstatus.exception.DuplicateTreatmentStatusApplicationException;
import com.migracion.rangel.application.treatmentstatus.command.UpdateTreatmentStatusCommand;
public class UpdateTreatmentStatusUseCase {
    private final TreatmentStatusRepository repository;
    public UpdateTreatmentStatusUseCase(TreatmentStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public TreatmentStatusResponse execute(UpdateTreatmentStatusCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new TreatmentStatusNotFoundApplicationException(id));
        if (repository.existsByCodeAndIdNot(command.code(), id)) { throw new DuplicateTreatmentStatusApplicationException("code"); }
        if (repository.existsByNameAndIdNot(command.name(), id)) { throw new DuplicateTreatmentStatusApplicationException("name"); }
        aggregate.update(command.code(), command.name(), command.active());
        return TreatmentStatusResponse.from(repository.save(aggregate));
    }
}


