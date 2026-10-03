package com.migracion.rangel.application.patientallergy.usecase;
import java.util.List;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.patientallergy.port.repository.PatientAllergyRepository;
import com.migracion.rangel.domain.patientallergy.model.valueobject.PatientAllergyId;
import com.migracion.rangel.domain.patientallergy.event.PatientAllergyDeletedEvent;
import com.migracion.rangel.application.patientallergy.dto.PatientAllergyResponse;
import com.migracion.rangel.application.patientallergy.exception.PatientAllergyNotFoundApplicationException;
public class ListPatientAllergyUseCase {
    private final PatientAllergyRepository repository;
    public ListPatientAllergyUseCase(PatientAllergyRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<PatientAllergyResponse> execute() {
        return repository.findAll().stream().map(PatientAllergyResponse::from).toList();
    }
}
