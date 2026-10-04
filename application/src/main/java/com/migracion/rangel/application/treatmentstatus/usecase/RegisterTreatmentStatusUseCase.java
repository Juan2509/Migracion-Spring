package com.migracion.rangel.application.treatmentstatus.usecase;
import com.migracion.rangel.domain.treatmentstatus.port.repository.TreatmentStatusRepository;
import com.migracion.rangel.domain.treatmentstatus.model.valueobject.TreatmentStatusId;
import com.migracion.rangel.application.treatmentstatus.dto.TreatmentStatusResponse;
import com.migracion.rangel.application.treatmentstatus.exception.TreatmentStatusNotFoundApplicationException;
import com.migracion.rangel.application.treatmentstatus.exception.DuplicateTreatmentStatusApplicationException;
import com.migracion.rangel.application.treatmentstatus.command.RegisterTreatmentStatusCommand;
import com.migracion.rangel.domain.treatmentstatus.model.aggregate.TreatmentStatus;
public class RegisterTreatmentStatusUseCase {
    private final TreatmentStatusRepository repository;
    public RegisterTreatmentStatusUseCase(TreatmentStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public TreatmentStatusResponse execute(RegisterTreatmentStatusCommand command) {
        var aggregate = TreatmentStatus.register(command.code(), command.name(), command.active());
        if (repository.existsByCode(command.code())) { throw new DuplicateTreatmentStatusApplicationException("code"); }
        if (repository.existsByName(command.name())) { throw new DuplicateTreatmentStatusApplicationException("name"); }
        return TreatmentStatusResponse.from(repository.save(aggregate));
    }
}


