package com.migracion.rangel.application.gender.usecase;
import com.migracion.rangel.domain.gender.port.repository.GenderRepository;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
import com.migracion.rangel.application.gender.dto.GenderResponse;
import com.migracion.rangel.application.gender.exception.GenderNotFoundApplicationException;
import com.migracion.rangel.application.gender.exception.DuplicateGenderApplicationException;
import com.migracion.rangel.application.gender.command.RegisterGenderCommand;
import com.migracion.rangel.domain.gender.model.aggregate.Gender;
public class RegisterGenderUseCase {
    private final GenderRepository repository;
    public RegisterGenderUseCase(GenderRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public GenderResponse execute(RegisterGenderCommand command) {
        var aggregate = Gender.register(command.description());
        if (repository.existsByDescription(command.description())) { throw new DuplicateGenderApplicationException(); }
        return GenderResponse.from(repository.save(aggregate));
    }
}
