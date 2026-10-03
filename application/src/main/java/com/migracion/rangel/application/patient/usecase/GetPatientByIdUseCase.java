package com.migracion.rangel.application.patient.usecase;
import java.util.List;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.patient.port.repository.PatientRepository;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.patient.event.PatientDeletedEvent;
import com.migracion.rangel.application.patient.dto.PatientResponse;
import com.migracion.rangel.application.patient.exception.PatientNotFoundApplicationException;
public class GetPatientByIdUseCase {
    private final PatientRepository repository;
    public GetPatientByIdUseCase(PatientRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public PatientResponse execute(PatientId id) {
        var patient = repository.findById(id).orElseThrow(() -> new PatientNotFoundApplicationException(id));
        return PatientResponse.from(patient);
    }
}
