package com.migracion.rangel.application.patientallergy.usecase;
import java.util.List;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.patientallergy.port.repository.PatientAllergyRepository;
import com.migracion.rangel.domain.patientallergy.model.valueobject.PatientAllergyId;
import com.migracion.rangel.domain.patientallergy.event.PatientAllergyDeletedEvent;
import com.migracion.rangel.application.patientallergy.dto.PatientAllergyResponse;
import com.migracion.rangel.application.patientallergy.exception.PatientAllergyNotFoundApplicationException;
public class GetPatientAllergyByIdUseCase {
    private final PatientAllergyRepository repository;
    public GetPatientAllergyByIdUseCase(PatientAllergyRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public PatientAllergyResponse execute(PatientAllergyId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new PatientAllergyNotFoundApplicationException(id));
        return PatientAllergyResponse.from(aggregate);
    }
}
