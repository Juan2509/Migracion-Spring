package com.migracion.rangel.application.clinicalrecordstatus.usecase;
import java.util.Objects;
import com.migracion.rangel.domain.clinicalrecordstatus.model.aggregate.ClinicalRecordStatus;
import com.migracion.rangel.domain.clinicalrecordstatus.port.repository.ClinicalRecordStatusRepository;
import com.migracion.rangel.application.clinicalrecordstatus.command.RegisterClinicalRecordStatusCommand;
import com.migracion.rangel.application.clinicalrecordstatus.dto.ClinicalRecordStatusResponse;
import com.migracion.rangel.application.clinicalrecordstatus.exception.ClinicalRecordStatusNotFoundApplicationException;
import com.migracion.rangel.application.clinicalrecordstatus.exception.DuplicateClinicalRecordStatusApplicationException;

public class RegisterClinicalRecordStatusUseCase {
    private final ClinicalRecordStatusRepository repository;

    public RegisterClinicalRecordStatusUseCase(ClinicalRecordStatusRepository repository) {
        this.repository = Objects.requireNonNull(repository);

    }
    public ClinicalRecordStatusResponse execute(RegisterClinicalRecordStatusCommand command) {
        var aggregate = ClinicalRecordStatus.register(command.code(), command.name());

        if (repository.existsByCode(command.code())) { throw new DuplicateClinicalRecordStatusApplicationException("code"); }
        if (repository.existsByName(command.name())) { throw new DuplicateClinicalRecordStatusApplicationException("name"); }
        return ClinicalRecordStatusResponse.from(repository.save(aggregate));
    }
}
