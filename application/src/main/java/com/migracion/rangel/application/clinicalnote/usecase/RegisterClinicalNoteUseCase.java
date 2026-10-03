package com.migracion.rangel.application.clinicalnote.usecase;
import java.util.Objects;
import com.migracion.rangel.domain.clinicalnote.model.aggregate.ClinicalNote;
import com.migracion.rangel.domain.clinicalnote.port.repository.ClinicalNoteRepository;
import com.migracion.rangel.application.clinicalnote.command.RegisterClinicalNoteCommand;
import com.migracion.rangel.application.clinicalnote.dto.ClinicalNoteResponse;
import com.migracion.rangel.application.clinicalnote.exception.ClinicalNoteNotFoundApplicationException;
import com.migracion.rangel.domain.encounter.port.repository.EncounterRepository;
import com.migracion.rangel.application.encounter.exception.EncounterNotFoundApplicationException;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
public class RegisterClinicalNoteUseCase {
    private final ClinicalNoteRepository repository;
    private final EncounterRepository encounters;
    private final ProfessionalRepository professionals;
    public RegisterClinicalNoteUseCase(ClinicalNoteRepository repository, EncounterRepository encounters, ProfessionalRepository professionals) {
        this.repository = Objects.requireNonNull(repository);
        this.encounters = Objects.requireNonNull(encounters);
        this.professionals = Objects.requireNonNull(professionals);
    }
    public ClinicalNoteResponse execute(RegisterClinicalNoteCommand command) {
        var aggregate = ClinicalNote.register(command.encounterId(), command.subjective(), command.objective(), command.assessment(), command.plan(), command.additionalNotes(), command.signedAt(), command.professionalId());
        encounters.findById(command.encounterId()).orElseThrow(() -> new EncounterNotFoundApplicationException(command.encounterId()));
        professionals.findById(command.professionalId()).orElseThrow(() -> new ProfessionalNotFoundApplicationException(command.professionalId()));
        return ClinicalNoteResponse.from(repository.save(aggregate));
    }
}

