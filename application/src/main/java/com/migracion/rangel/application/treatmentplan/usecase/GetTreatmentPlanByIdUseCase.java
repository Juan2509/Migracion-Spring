package com.migracion.rangel.application.treatmentplan.usecase;
import java.util.List;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.treatmentplan.port.repository.TreatmentPlanRepository;
import com.migracion.rangel.domain.treatmentplan.model.valueobject.TreatmentPlanId;
import com.migracion.rangel.domain.treatmentplan.event.TreatmentPlanDeletedEvent;
import com.migracion.rangel.application.treatmentplan.dto.TreatmentPlanResponse;
import com.migracion.rangel.application.treatmentplan.exception.TreatmentPlanNotFoundApplicationException;
public class GetTreatmentPlanByIdUseCase {
    private final TreatmentPlanRepository repository;
    public GetTreatmentPlanByIdUseCase(TreatmentPlanRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public TreatmentPlanResponse execute(TreatmentPlanId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new TreatmentPlanNotFoundApplicationException(id));
        return TreatmentPlanResponse.from(aggregate);
    }
}


