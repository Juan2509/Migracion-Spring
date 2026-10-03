package com.migracion.rangel.application.clinicalrecord.usecase;
import java.util.List;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.clinicalrecord.port.repository.ClinicalRecordRepository;
import com.migracion.rangel.domain.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.migracion.rangel.domain.clinicalrecord.event.ClinicalRecordDeletedEvent;
import com.migracion.rangel.application.clinicalrecord.dto.ClinicalRecordResponse;
import com.migracion.rangel.application.clinicalrecord.exception.ClinicalRecordNotFoundApplicationException;
public class DeleteClinicalRecordUseCase {
    private final ClinicalRecordRepository repository;
    public DeleteClinicalRecordUseCase(ClinicalRecordRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ClinicalRecordDeletedEvent execute(ClinicalRecordId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new ClinicalRecordNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new ClinicalRecordDeletedEvent(id, LocalDateTime.now());
    }
}
