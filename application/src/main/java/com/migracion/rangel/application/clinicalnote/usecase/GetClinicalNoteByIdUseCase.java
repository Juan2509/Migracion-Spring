package com.migracion.rangel.application.clinicalnote.usecase;
import java.util.List;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.clinicalnote.port.repository.ClinicalNoteRepository;
import com.migracion.rangel.domain.clinicalnote.model.valueobject.ClinicalNoteId;
import com.migracion.rangel.domain.clinicalnote.event.ClinicalNoteDeletedEvent;
import com.migracion.rangel.application.clinicalnote.dto.ClinicalNoteResponse;
import com.migracion.rangel.application.clinicalnote.exception.ClinicalNoteNotFoundApplicationException;
public class GetClinicalNoteByIdUseCase {
    private final ClinicalNoteRepository repository;
    public GetClinicalNoteByIdUseCase(ClinicalNoteRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ClinicalNoteResponse execute(ClinicalNoteId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new ClinicalNoteNotFoundApplicationException(id));
        return ClinicalNoteResponse.from(aggregate);
    }
}

