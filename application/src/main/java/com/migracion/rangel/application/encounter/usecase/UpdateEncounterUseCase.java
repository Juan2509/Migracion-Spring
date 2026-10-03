package com.migracion.rangel.application.encounter.usecase;
import java.util.Objects;
import com.migracion.rangel.domain.encounter.model.aggregate.Encounter;
import com.migracion.rangel.domain.encounter.port.repository.EncounterRepository;
import com.migracion.rangel.application.encounter.command.UpdateEncounterCommand;
import com.migracion.rangel.application.encounter.dto.EncounterResponse;
import com.migracion.rangel.application.encounter.exception.EncounterNotFoundApplicationException;
import com.migracion.rangel.domain.clinicalrecord.port.repository.ClinicalRecordRepository;
import com.migracion.rangel.application.clinicalrecord.exception.ClinicalRecordNotFoundApplicationException;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
import com.migracion.rangel.domain.encountertype.port.repository.EncounterTypeRepository;
import com.migracion.rangel.application.encountertype.exception.EncounterTypeNotFoundApplicationException;
import com.migracion.rangel.domain.encountermodality.port.repository.EncounterModalityRepository;
import com.migracion.rangel.application.encountermodality.exception.EncounterModalityNotFoundApplicationException;
import com.migracion.rangel.domain.encounterstatus.port.repository.EncounterStatusRepository;
import com.migracion.rangel.application.encounterstatus.exception.EncounterStatusNotFoundApplicationException;
public class UpdateEncounterUseCase {
    private final EncounterRepository repository;
    private final ClinicalRecordRepository records;
    private final ProfessionalRepository professionals;
    private final EncounterTypeRepository types;
    private final EncounterModalityRepository modalities;
    private final EncounterStatusRepository statuses;
    public UpdateEncounterUseCase(EncounterRepository repository, ClinicalRecordRepository records, ProfessionalRepository professionals, EncounterTypeRepository types, EncounterModalityRepository modalities, EncounterStatusRepository statuses) {
        this.repository = Objects.requireNonNull(repository);
        this.records = Objects.requireNonNull(records);
        this.professionals = Objects.requireNonNull(professionals);
        this.types = Objects.requireNonNull(types);
        this.modalities = Objects.requireNonNull(modalities);
        this.statuses = Objects.requireNonNull(statuses);
    }
    public EncounterResponse execute(UpdateEncounterCommand command) {
        var aggregate = repository.findById(command.id()).orElseThrow(() -> new EncounterNotFoundApplicationException(command.id()));
        records.findById(command.clinicalRecordId()).orElseThrow(() -> new ClinicalRecordNotFoundApplicationException(command.clinicalRecordId()));
        professionals.findById(command.professionalId()).orElseThrow(() -> new ProfessionalNotFoundApplicationException(command.professionalId()));
        types.findById(command.encounterTypeId()).orElseThrow(() -> new EncounterTypeNotFoundApplicationException(command.encounterTypeId()));
        modalities.findById(command.modalityId()).orElseThrow(() -> new EncounterModalityNotFoundApplicationException(command.modalityId()));
        statuses.findById(command.statusId()).orElseThrow(() -> new EncounterStatusNotFoundApplicationException(command.statusId()));

        professionals.findById(command.updatedBy()).orElseThrow(() -> new ProfessionalNotFoundApplicationException(command.updatedBy()));
        aggregate.update(command.clinicalRecordId(), command.professionalId(), command.encounterTypeId(), command.startedAt(), command.endedAt(), command.reasonForVisit(), command.currentCondition(), command.modalityId(), command.statusId(), command.updatedBy());
        return EncounterResponse.from(repository.save(aggregate));
    }
}
