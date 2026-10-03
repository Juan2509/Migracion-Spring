package com.migracion.rangel.application.study.usecase;
import com.migracion.rangel.domain.study.port.repository.StudyRepository;
import com.migracion.rangel.domain.study.model.valueobject.StudyId;
import com.migracion.rangel.application.study.dto.StudyResponse;
import com.migracion.rangel.application.study.exception.StudyNotFoundApplicationException;
import java.util.List;
public class ListStudyUseCase {
    private final StudyRepository repository;
    public ListStudyUseCase(StudyRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<StudyResponse> execute() { return repository.findAll().stream().map(StudyResponse::from).toList(); }
}
