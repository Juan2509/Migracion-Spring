package com.migracion.rangel.application.clinicalnote.usecase;
import java.util.List;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.clinicalnote.port.repository.ClinicalNoteRepository;
import com.migracion.rangel.domain.clinicalnote.model.valueobject.ClinicalNoteId;
import com.migracion.rangel.domain.clinicalnote.event.ClinicalNoteDeletedEvent;
import com.migracion.rangel.application.clinicalnote.dto.ClinicalNoteResponse;
import com.migracion.rangel.application.clinicalnote.exception.ClinicalNoteNotFoundApplicationException;
public class ListClinicalNoteUseCase {
    private final ClinicalNoteRepository repository;
    public ListClinicalNoteUseCase(ClinicalNoteRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<ClinicalNoteResponse> execute() {
        return repository.findAll().stream().map(ClinicalNoteResponse::from).toList();
    }
}

