package com.migracion.rangel.application.clinicalrecord.usecase;
import java.util.Objects;
import com.migracion.rangel.domain.clinicalrecord.model.aggregate.ClinicalRecord;
import com.migracion.rangel.domain.clinicalrecord.port.repository.ClinicalRecordRepository;
import com.migracion.rangel.application.clinicalrecord.command.RegisterClinicalRecordCommand;
import com.migracion.rangel.application.clinicalrecord.dto.ClinicalRecordResponse;
import com.migracion.rangel.application.clinicalrecord.exception.ClinicalRecordNotFoundApplicationException;

import com.migracion.rangel.domain.patient.port.repository.PatientRepository;
import com.migracion.rangel.application.patient.exception.PatientNotFoundApplicationException;
import com.migracion.rangel.domain.clinicalrecordstatus.port.repository.ClinicalRecordStatusRepository;
import com.migracion.rangel.application.clinicalrecordstatus.exception.ClinicalRecordStatusNotFoundApplicationException;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
public class RegisterClinicalRecordUseCase {
    private final ClinicalRecordRepository repository;
    private final PatientRepository patients;
    private final ClinicalRecordStatusRepository statuses;
    private final ProfessionalRepository professionals;
    public RegisterClinicalRecordUseCase(ClinicalRecordRepository repository, PatientRepository patients, ClinicalRecordStatusRepository statuses, ProfessionalRepository professionals) {
        this.repository = Objects.requireNonNull(repository);
        this.patients = Objects.requireNonNull(patients);
        this.statuses = Objects.requireNonNull(statuses);
        this.professionals = Objects.requireNonNull(professionals);
    }
    public ClinicalRecordResponse execute(RegisterClinicalRecordCommand command) {
        var aggregate = ClinicalRecord.register(command.patientId(), command.creationDate(), command.recordNumber(), command.openedAt(), command.closedAt(), command.statusId(), command.createdBy());
        patients.findById(command.patientId()).orElseThrow(() -> new PatientNotFoundApplicationException(command.patientId()));
        statuses.findById(command.statusId()).orElseThrow(() -> new ClinicalRecordStatusNotFoundApplicationException(command.statusId()));
        professionals.findById(command.createdBy()).orElseThrow(() -> new ProfessionalNotFoundApplicationException(command.createdBy()));

        return ClinicalRecordResponse.from(repository.save(aggregate));
    }
}
