package com.migracion.rangel.application.clinicalrecordstatus.usecase;
import java.util.Objects;
import com.migracion.rangel.domain.clinicalrecordstatus.model.aggregate.ClinicalRecordStatus;
import com.migracion.rangel.domain.clinicalrecordstatus.port.repository.ClinicalRecordStatusRepository;
import com.migracion.rangel.application.clinicalrecordstatus.command.UpdateClinicalRecordStatusCommand;
import com.migracion.rangel.application.clinicalrecordstatus.dto.ClinicalRecordStatusResponse;
import com.migracion.rangel.application.clinicalrecordstatus.exception.ClinicalRecordStatusNotFoundApplicationException;
import com.migracion.rangel.application.clinicalrecordstatus.exception.DuplicateClinicalRecordStatusApplicationException;

public class UpdateClinicalRecordStatusUseCase {
    private final ClinicalRecordStatusRepository repository;

    public UpdateClinicalRecordStatusUseCase(ClinicalRecordStatusRepository repository) {
        this.repository = Objects.requireNonNull(repository);

    }
    public ClinicalRecordStatusResponse execute(UpdateClinicalRecordStatusCommand command) {
        var aggregate = repository.findById(command.id()).orElseThrow(() -> new ClinicalRecordStatusNotFoundApplicationException(command.id()));

        if (repository.existsByCodeAndIdNot(command.code(), command.id())) { throw new DuplicateClinicalRecordStatusApplicationException("code"); }
        if (repository.existsByNameAndIdNot(command.name(), command.id())) { throw new DuplicateClinicalRecordStatusApplicationException("name"); }
        aggregate.update(command.code(), command.name());
        return ClinicalRecordStatusResponse.from(repository.save(aggregate));
    }
}
