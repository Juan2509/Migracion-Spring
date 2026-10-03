package com.migracion.rangel.application.study.usecase;
import com.migracion.rangel.domain.study.port.repository.StudyRepository;
import com.migracion.rangel.domain.study.model.valueobject.StudyId;
import com.migracion.rangel.application.study.dto.StudyResponse;
import com.migracion.rangel.application.study.exception.StudyNotFoundApplicationException;
import com.migracion.rangel.application.study.command.RegisterStudyCommand;
import com.migracion.rangel.domain.study.model.aggregate.Study;
public class RegisterStudyUseCase {
    private final StudyRepository repository;
    public RegisterStudyUseCase(StudyRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public StudyResponse execute(RegisterStudyCommand command) {
        var aggregate = Study.register(command.name());
        return StudyResponse.from(repository.save(aggregate));
    }
}
