package com.migracion.rangel.application.patient.usecase;
import java.util.List;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.patient.port.repository.PatientRepository;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.patient.event.PatientDeletedEvent;
import com.migracion.rangel.application.patient.dto.PatientResponse;
import com.migracion.rangel.application.patient.exception.PatientNotFoundApplicationException;
public class ListPatientUseCase {
    private final PatientRepository repository;
    public ListPatientUseCase(PatientRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<PatientResponse> execute() {
        return repository.findAll().stream().map(PatientResponse::from).toList();
    }
}
