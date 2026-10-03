package com.migracion.rangel.application.mentalstatusexam.usecase;
import java.util.Objects;
import com.migracion.rangel.domain.mentalstatusexam.model.aggregate.MentalStatusExam;
import com.migracion.rangel.domain.mentalstatusexam.port.repository.MentalStatusExamRepository;
import com.migracion.rangel.application.mentalstatusexam.command.UpdateMentalStatusExamCommand;
import com.migracion.rangel.application.mentalstatusexam.dto.MentalStatusExamResponse;
import com.migracion.rangel.application.mentalstatusexam.exception.MentalStatusExamNotFoundApplicationException;
import com.migracion.rangel.domain.encounter.port.repository.EncounterRepository;
import com.migracion.rangel.application.encounter.exception.EncounterNotFoundApplicationException;

public class UpdateMentalStatusExamUseCase {
    private final MentalStatusExamRepository repository;
    private final EncounterRepository encounters;

    public UpdateMentalStatusExamUseCase(MentalStatusExamRepository repository, EncounterRepository encounters) {
        this.repository = Objects.requireNonNull(repository);
        this.encounters = Objects.requireNonNull(encounters);

    }
    public MentalStatusExamResponse execute(UpdateMentalStatusExamCommand command) {
        var aggregate = repository.findById(command.id()).orElseThrow(() -> new MentalStatusExamNotFoundApplicationException(command.id()));
        encounters.findById(command.encounterId()).orElseThrow(() -> new EncounterNotFoundApplicationException(command.encounterId()));
        aggregate.update(command.encounterId(), command.appearance(), command.behavior(), command.attitude(), command.consciousness(), command.orientation(), command.attention(), command.memory(), command.speech(), command.mood(), command.affect(), command.thoughtProcess(), command.thoughtContent(), command.perception(), command.judgment(), command.insight(), command.psychomotorActivity(), command.observations());
        return MentalStatusExamResponse.from(repository.save(aggregate));
    }
}
