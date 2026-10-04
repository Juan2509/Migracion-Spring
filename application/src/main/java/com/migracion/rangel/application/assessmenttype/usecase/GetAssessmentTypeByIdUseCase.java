package com.migracion.rangel.application.assessmenttype.usecase;
import com.migracion.rangel.domain.assessmenttype.port.repository.AssessmentTypeRepository;
import com.migracion.rangel.domain.assessmenttype.model.valueobject.AssessmentTypeId;
import com.migracion.rangel.application.assessmenttype.dto.AssessmentTypeResponse;
import com.migracion.rangel.application.assessmenttype.exception.AssessmentTypeNotFoundApplicationException;
import com.migracion.rangel.application.assessmenttype.exception.DuplicateAssessmentTypeApplicationException;

public class GetAssessmentTypeByIdUseCase {
    private final AssessmentTypeRepository repository;
    public GetAssessmentTypeByIdUseCase(AssessmentTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public AssessmentTypeResponse execute(AssessmentTypeId id) { return AssessmentTypeResponse.from(repository.findById(id).orElseThrow(() -> new AssessmentTypeNotFoundApplicationException(id))); }
}

