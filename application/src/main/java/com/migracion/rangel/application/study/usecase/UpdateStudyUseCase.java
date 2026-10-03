package com.migracion.rangel.application.study.usecase;
import com.migracion.rangel.domain.study.port.repository.StudyRepository;
import com.migracion.rangel.domain.study.model.valueobject.StudyId;
import com.migracion.rangel.application.study.dto.StudyResponse;
import com.migracion.rangel.application.study.exception.StudyNotFoundApplicationException;
import com.migracion.rangel.application.study.command.UpdateStudyCommand;
public class UpdateStudyUseCase {
    private final StudyRepository repository;
    public UpdateStudyUseCase(StudyRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public StudyResponse execute(UpdateStudyCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new StudyNotFoundApplicationException(id));
        aggregate.update(command.name());
        return StudyResponse.from(repository.save(aggregate));
    }
}
