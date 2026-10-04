package com.migracion.rangel.application.treatmentplan.usecase;
import java.util.Objects;
import com.migracion.rangel.domain.treatmentplan.model.aggregate.TreatmentPlan;
import com.migracion.rangel.domain.treatmentplan.port.repository.TreatmentPlanRepository;
import com.migracion.rangel.application.treatmentplan.command.RegisterTreatmentPlanCommand;
import com.migracion.rangel.application.treatmentplan.dto.TreatmentPlanResponse;
import com.migracion.rangel.application.treatmentplan.exception.TreatmentPlanNotFoundApplicationException;
import com.migracion.rangel.domain.encounter.port.repository.EncounterRepository;
import com.migracion.rangel.application.encounter.exception.EncounterNotFoundApplicationException;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.domain.treatmentstatus.port.repository.TreatmentStatusRepository;
import com.migracion.rangel.application.treatmentstatus.exception.TreatmentStatusNotFoundApplicationException;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;
public class RegisterTreatmentPlanUseCase {
    private final TreatmentPlanRepository repository;
    private final EncounterRepository encounters;
    private final ProfessionalRepository professionals;
    private final TreatmentStatusRepository statuses;
    public RegisterTreatmentPlanUseCase(TreatmentPlanRepository repository, EncounterRepository encounters, ProfessionalRepository professionals, TreatmentStatusRepository statuses) {
        this.repository = Objects.requireNonNull(repository);
        this.encounters = Objects.requireNonNull(encounters);
        this.professionals = Objects.requireNonNull(professionals);
        this.statuses = Objects.requireNonNull(statuses);
    }
    public TreatmentPlanResponse execute(RegisterTreatmentPlanCommand command) {
        var aggregate = TreatmentPlan.register(command.encounterId(), command.title(), command.description(), command.startDate(), command.endDate(), command.treatmentStatusId(), command.professionalId());
        encounters.findById(command.encounterId()).orElseThrow(() -> new EncounterNotFoundApplicationException(command.encounterId()));
        professionals.findById(command.professionalId()).orElseThrow(() -> new ProfessionalNotFoundApplicationException(command.professionalId()));
        statuses.findById(command.treatmentStatusId()).orElseThrow(() -> new TreatmentStatusNotFoundApplicationException(command.treatmentStatusId()));
        return TreatmentPlanResponse.from(repository.save(aggregate));
    }
}


