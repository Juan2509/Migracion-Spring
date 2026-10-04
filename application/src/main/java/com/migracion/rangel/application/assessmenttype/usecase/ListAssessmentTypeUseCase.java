package com.migracion.rangel.application.assessmenttype.usecase;
import com.migracion.rangel.domain.assessmenttype.port.repository.AssessmentTypeRepository;
import com.migracion.rangel.domain.assessmenttype.model.valueobject.AssessmentTypeId;
import com.migracion.rangel.application.assessmenttype.dto.AssessmentTypeResponse;
import com.migracion.rangel.application.assessmenttype.exception.AssessmentTypeNotFoundApplicationException;
import com.migracion.rangel.application.assessmenttype.exception.DuplicateAssessmentTypeApplicationException;
import java.util.List;
public class ListAssessmentTypeUseCase {
    private final AssessmentTypeRepository repository;
    public ListAssessmentTypeUseCase(AssessmentTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<AssessmentTypeResponse> execute() { return repository.findAll().stream().map(AssessmentTypeResponse::from).toList(); }
}

