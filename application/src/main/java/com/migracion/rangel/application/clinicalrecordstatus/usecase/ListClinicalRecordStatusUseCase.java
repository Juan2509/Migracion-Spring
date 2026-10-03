package com.migracion.rangel.application.clinicalrecordstatus.usecase;
import java.util.List;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.clinicalrecordstatus.port.repository.ClinicalRecordStatusRepository;
import com.migracion.rangel.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import com.migracion.rangel.domain.clinicalrecordstatus.event.ClinicalRecordStatusDeletedEvent;
import com.migracion.rangel.application.clinicalrecordstatus.dto.ClinicalRecordStatusResponse;
import com.migracion.rangel.application.clinicalrecordstatus.exception.ClinicalRecordStatusNotFoundApplicationException;
public class ListClinicalRecordStatusUseCase {
    private final ClinicalRecordStatusRepository repository;
    public ListClinicalRecordStatusUseCase(ClinicalRecordStatusRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<ClinicalRecordStatusResponse> execute() {
        return repository.findAll().stream().map(ClinicalRecordStatusResponse::from).toList();
    }
}
