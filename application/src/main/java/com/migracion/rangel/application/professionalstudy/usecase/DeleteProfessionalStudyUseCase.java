package com.migracion.rangel.application.professionalstudy.usecase;
import com.migracion.rangel.domain.professionalstudy.port.repository.ProfessionalStudyRepository;
import com.migracion.rangel.domain.professionalstudy.model.valueobject.ProfessionalStudyId;
import com.migracion.rangel.application.professionalstudy.dto.ProfessionalStudyResponse;
import com.migracion.rangel.application.professionalstudy.exception.ProfessionalStudyNotFoundApplicationException;

import java.time.LocalDateTime;
import com.migracion.rangel.domain.professionalstudy.event.ProfessionalStudyDeletedEvent;
public class DeleteProfessionalStudyUseCase {
    private final ProfessionalStudyRepository repository;

    public DeleteProfessionalStudyUseCase(ProfessionalStudyRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public ProfessionalStudyDeletedEvent execute(ProfessionalStudyId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new ProfessionalStudyNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new ProfessionalStudyDeletedEvent(id, LocalDateTime.now());
    }
}
