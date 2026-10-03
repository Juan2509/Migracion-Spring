package com.migracion.rangel.application.gender.usecase;
import com.migracion.rangel.domain.gender.port.repository.GenderRepository;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
import com.migracion.rangel.application.gender.dto.GenderResponse;
import com.migracion.rangel.application.gender.exception.GenderNotFoundApplicationException;
import com.migracion.rangel.application.gender.exception.DuplicateGenderApplicationException;
import com.migracion.rangel.application.gender.command.UpdateGenderCommand;
public class UpdateGenderUseCase {
    private final GenderRepository repository;
    public UpdateGenderUseCase(GenderRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public GenderResponse execute(UpdateGenderCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new GenderNotFoundApplicationException(id));
        if (repository.existsByDescriptionAndIdNot(command.description(), id)) { throw new DuplicateGenderApplicationException(); }
        aggregate.update(command.description());
        return GenderResponse.from(repository.save(aggregate));
    }
}
