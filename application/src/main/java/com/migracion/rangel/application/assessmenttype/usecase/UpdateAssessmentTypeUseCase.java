package com.migracion.rangel.application.assessmenttype.usecase;
import com.migracion.rangel.domain.assessmenttype.port.repository.AssessmentTypeRepository;
import com.migracion.rangel.domain.assessmenttype.model.valueobject.AssessmentTypeId;
import com.migracion.rangel.application.assessmenttype.dto.AssessmentTypeResponse;
import com.migracion.rangel.application.assessmenttype.exception.AssessmentTypeNotFoundApplicationException;
import com.migracion.rangel.application.assessmenttype.exception.DuplicateAssessmentTypeApplicationException;
import com.migracion.rangel.application.assessmenttype.command.UpdateAssessmentTypeCommand;
public class UpdateAssessmentTypeUseCase {
    private final AssessmentTypeRepository repository;
    public UpdateAssessmentTypeUseCase(AssessmentTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public AssessmentTypeResponse execute(UpdateAssessmentTypeCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new AssessmentTypeNotFoundApplicationException(id));
        if (repository.existsByCodeAndIdNot(command.code(), id)) { throw new DuplicateAssessmentTypeApplicationException(); }
        aggregate.update(command.code(), command.name(), command.active(), command.description());
        return AssessmentTypeResponse.from(repository.save(aggregate));
    }
}

