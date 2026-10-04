package com.migracion.rangel.application.assessmenttype.usecase;
import com.migracion.rangel.domain.assessmenttype.port.repository.AssessmentTypeRepository;
import com.migracion.rangel.domain.assessmenttype.model.valueobject.AssessmentTypeId;
import com.migracion.rangel.application.assessmenttype.dto.AssessmentTypeResponse;
import com.migracion.rangel.application.assessmenttype.exception.AssessmentTypeNotFoundApplicationException;
import com.migracion.rangel.application.assessmenttype.exception.DuplicateAssessmentTypeApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.assessmenttype.event.AssessmentTypeDeletedEvent;
public class DeleteAssessmentTypeUseCase {
    private final AssessmentTypeRepository repository;
    public DeleteAssessmentTypeUseCase(AssessmentTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public AssessmentTypeDeletedEvent execute(AssessmentTypeId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new AssessmentTypeNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new AssessmentTypeDeletedEvent(id, LocalDateTime.now());
    }
}

