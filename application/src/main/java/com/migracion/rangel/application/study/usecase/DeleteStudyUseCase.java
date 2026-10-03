package com.migracion.rangel.application.study.usecase;
import com.migracion.rangel.domain.study.port.repository.StudyRepository;
import com.migracion.rangel.domain.study.model.valueobject.StudyId;
import com.migracion.rangel.application.study.dto.StudyResponse;
import com.migracion.rangel.application.study.exception.StudyNotFoundApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.study.event.StudyDeletedEvent;
public class DeleteStudyUseCase {
    private final StudyRepository repository;
    public DeleteStudyUseCase(StudyRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public StudyDeletedEvent execute(StudyId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new StudyNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new StudyDeletedEvent(id, LocalDateTime.now());
    }
}
