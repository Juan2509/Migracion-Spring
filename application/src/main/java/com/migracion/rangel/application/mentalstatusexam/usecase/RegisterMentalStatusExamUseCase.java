package com.migracion.rangel.application.mentalstatusexam.usecase;
import java.util.Objects;
import com.migracion.rangel.domain.mentalstatusexam.model.aggregate.MentalStatusExam;
import com.migracion.rangel.domain.mentalstatusexam.port.repository.MentalStatusExamRepository;
import com.migracion.rangel.application.mentalstatusexam.command.RegisterMentalStatusExamCommand;
import com.migracion.rangel.application.mentalstatusexam.dto.MentalStatusExamResponse;
import com.migracion.rangel.application.mentalstatusexam.exception.MentalStatusExamNotFoundApplicationException;
import com.migracion.rangel.domain.encounter.port.repository.EncounterRepository;
import com.migracion.rangel.application.encounter.exception.EncounterNotFoundApplicationException;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
public class RegisterMentalStatusExamUseCase {
    private final MentalStatusExamRepository repository;
    private final EncounterRepository encounters;
    private final ProfessionalRepository professionals;
    public RegisterMentalStatusExamUseCase(MentalStatusExamRepository repository, EncounterRepository encounters, ProfessionalRepository professionals) {
        this.repository = Objects.requireNonNull(repository);
        this.encounters = Objects.requireNonNull(encounters);
        this.professionals = Objects.requireNonNull(professionals);
    }
    public MentalStatusExamResponse execute(RegisterMentalStatusExamCommand command) {
        var aggregate = MentalStatusExam.register(command.encounterId(), command.appearance(), command.behavior(), command.attitude(), command.consciousness(), command.orientation(), command.attention(), command.memory(), command.speech(), command.mood(), command.affect(), command.thoughtProcess(), command.thoughtContent(), command.perception(), command.judgment(), command.insight(), command.psychomotorActivity(), command.observations(), command.createdBy());
        encounters.findById(command.encounterId()).orElseThrow(() -> new EncounterNotFoundApplicationException(command.encounterId()));
        professionals.findById(command.createdBy()).orElseThrow(() -> new ProfessionalNotFoundApplicationException(command.createdBy()));
        return MentalStatusExamResponse.from(repository.save(aggregate));
    }
}
