package com.migracion.rangel.application.assessmenttype.usecase;
import com.migracion.rangel.domain.assessmenttype.port.repository.AssessmentTypeRepository;
import com.migracion.rangel.domain.assessmenttype.model.valueobject.AssessmentTypeId;
import com.migracion.rangel.application.assessmenttype.dto.AssessmentTypeResponse;
import com.migracion.rangel.application.assessmenttype.exception.AssessmentTypeNotFoundApplicationException;
import com.migracion.rangel.application.assessmenttype.exception.DuplicateAssessmentTypeApplicationException;
import com.migracion.rangel.application.assessmenttype.command.RegisterAssessmentTypeCommand;
import com.migracion.rangel.domain.assessmenttype.model.aggregate.AssessmentType;
public class RegisterAssessmentTypeUseCase {
    private final AssessmentTypeRepository repository;
    public RegisterAssessmentTypeUseCase(AssessmentTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public AssessmentTypeResponse execute(RegisterAssessmentTypeCommand command) {
        var aggregate = AssessmentType.register(command.code(), command.name(), command.active(), command.description());
        if (repository.existsByCode(command.code())) { throw new DuplicateAssessmentTypeApplicationException(); }
        return AssessmentTypeResponse.from(repository.save(aggregate));
    }
}

