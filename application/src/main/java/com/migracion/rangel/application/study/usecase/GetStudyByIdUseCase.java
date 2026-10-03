package com.migracion.rangel.application.study.usecase;
import com.migracion.rangel.domain.study.port.repository.StudyRepository;
import com.migracion.rangel.domain.study.model.valueobject.StudyId;
import com.migracion.rangel.application.study.dto.StudyResponse;
import com.migracion.rangel.application.study.exception.StudyNotFoundApplicationException;

public class GetStudyByIdUseCase {
    private final StudyRepository repository;
    public GetStudyByIdUseCase(StudyRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public StudyResponse execute(StudyId id) { return StudyResponse.from(repository.findById(id).orElseThrow(() -> new StudyNotFoundApplicationException(id))); }
}
