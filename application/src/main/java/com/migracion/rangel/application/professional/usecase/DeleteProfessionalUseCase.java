package com.migracion.rangel.application.professional.usecase;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.application.professional.dto.ProfessionalResponse;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import com.migracion.rangel.domain.professional.event.ProfessionalDeletedEvent;
public class DeleteProfessionalUseCase {
    private final ProfessionalRepository repository;

    public DeleteProfessionalUseCase(ProfessionalRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public ProfessionalDeletedEvent execute(ProfessionalId id) {
        var professional = repository.findById(id).orElseThrow(() -> new ProfessionalNotFoundApplicationException(id));
        repository.delete(professional);
        return new ProfessionalDeletedEvent(id, LocalDateTime.now(ZoneOffset.UTC));
    }
}
