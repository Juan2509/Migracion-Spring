package com.migracion.rangel.application.patientcontact.usecase;
import java.util.List;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.patientcontact.port.repository.PatientContactRepository;
import com.migracion.rangel.domain.patientcontact.model.valueobject.PatientContactId;
import com.migracion.rangel.domain.patientcontact.event.PatientContactDeletedEvent;
import com.migracion.rangel.application.patientcontact.dto.PatientContactResponse;
import com.migracion.rangel.application.patientcontact.exception.PatientContactNotFoundApplicationException;
public class ListPatientContactUseCase {
    private final PatientContactRepository repository;
    public ListPatientContactUseCase(PatientContactRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<PatientContactResponse> execute() {
        return repository.findAll().stream().map(PatientContactResponse::from).toList();
    }
}
