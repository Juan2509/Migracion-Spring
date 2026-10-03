package com.migracion.rangel.application.clinicalrecord.usecase;
import java.util.Objects;
import com.migracion.rangel.domain.clinicalrecord.model.aggregate.ClinicalRecord;
import com.migracion.rangel.domain.clinicalrecord.port.repository.ClinicalRecordRepository;
import com.migracion.rangel.application.clinicalrecord.command.UpdateClinicalRecordCommand;
import com.migracion.rangel.application.clinicalrecord.dto.ClinicalRecordResponse;
import com.migracion.rangel.application.clinicalrecord.exception.ClinicalRecordNotFoundApplicationException;

import com.migracion.rangel.domain.patient.port.repository.PatientRepository;
import com.migracion.rangel.application.patient.exception.PatientNotFoundApplicationException;
import com.migracion.rangel.domain.clinicalrecordstatus.port.repository.ClinicalRecordStatusRepository;
import com.migracion.rangel.application.clinicalrecordstatus.exception.ClinicalRecordStatusNotFoundApplicationException;
public class UpdateClinicalRecordUseCase {
    private final ClinicalRecordRepository repository;
    private final PatientRepository patients;
    private final ClinicalRecordStatusRepository statuses;
    public UpdateClinicalRecordUseCase(ClinicalRecordRepository repository, PatientRepository patients, ClinicalRecordStatusRepository statuses) {
        this.repository = Objects.requireNonNull(repository);
        this.patients = Objects.requireNonNull(patients);
        this.statuses = Objects.requireNonNull(statuses);
    }
    public ClinicalRecordResponse execute(UpdateClinicalRecordCommand command) {
        var aggregate = repository.findById(command.id()).orElseThrow(() -> new ClinicalRecordNotFoundApplicationException(command.id()));
        patients.findById(command.patientId()).orElseThrow(() -> new PatientNotFoundApplicationException(command.patientId()));
        statuses.findById(command.statusId()).orElseThrow(() -> new ClinicalRecordStatusNotFoundApplicationException(command.statusId()));

        aggregate.update(command.patientId(), command.creationDate(), command.recordNumber(), command.openedAt(), command.closedAt(), command.statusId());
        return ClinicalRecordResponse.from(repository.save(aggregate));
    }
}
