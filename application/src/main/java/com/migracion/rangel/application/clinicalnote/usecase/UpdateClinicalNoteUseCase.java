package com.migracion.rangel.application.clinicalnote.usecase;
import java.util.Objects;
import com.migracion.rangel.domain.clinicalnote.model.aggregate.ClinicalNote;
import com.migracion.rangel.domain.clinicalnote.port.repository.ClinicalNoteRepository;
import com.migracion.rangel.application.clinicalnote.command.UpdateClinicalNoteCommand;
import com.migracion.rangel.application.clinicalnote.dto.ClinicalNoteResponse;
import com.migracion.rangel.application.clinicalnote.exception.ClinicalNoteNotFoundApplicationException;
import com.migracion.rangel.domain.encounter.port.repository.EncounterRepository;
import com.migracion.rangel.application.encounter.exception.EncounterNotFoundApplicationException;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
public class UpdateClinicalNoteUseCase {
    private final ClinicalNoteRepository repository;
    private final EncounterRepository encounters;
    private final ProfessionalRepository professionals;
    public UpdateClinicalNoteUseCase(ClinicalNoteRepository repository, EncounterRepository encounters, ProfessionalRepository professionals) {
        this.repository = Objects.requireNonNull(repository);
        this.encounters = Objects.requireNonNull(encounters);
        this.professionals = Objects.requireNonNull(professionals);
    }
    public ClinicalNoteResponse execute(UpdateClinicalNoteCommand command) {
        var aggregate = repository.findById(command.id()).orElseThrow(() -> new ClinicalNoteNotFoundApplicationException(command.id()));
        encounters.findById(command.encounterId()).orElseThrow(() -> new EncounterNotFoundApplicationException(command.encounterId()));
        professionals.findById(command.professionalId()).orElseThrow(() -> new ProfessionalNotFoundApplicationException(command.professionalId()));
        aggregate.update(command.encounterId(), command.subjective(), command.objective(), command.assessment(), command.plan(), command.additionalNotes(), command.signedAt(), command.professionalId());
        return ClinicalNoteResponse.from(repository.save(aggregate));
    }
}

