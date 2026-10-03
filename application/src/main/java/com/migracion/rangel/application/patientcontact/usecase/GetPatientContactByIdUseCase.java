package com.migracion.rangel.application.patientcontact.usecase;
import java.util.List;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.patientcontact.port.repository.PatientContactRepository;
import com.migracion.rangel.domain.patientcontact.model.valueobject.PatientContactId;
import com.migracion.rangel.domain.patientcontact.event.PatientContactDeletedEvent;
import com.migracion.rangel.application.patientcontact.dto.PatientContactResponse;
import com.migracion.rangel.application.patientcontact.exception.PatientContactNotFoundApplicationException;
public class GetPatientContactByIdUseCase {
    private final PatientContactRepository repository;
    public GetPatientContactByIdUseCase(PatientContactRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public PatientContactResponse execute(PatientContactId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new PatientContactNotFoundApplicationException(id));
        return PatientContactResponse.from(aggregate);
    }
}
