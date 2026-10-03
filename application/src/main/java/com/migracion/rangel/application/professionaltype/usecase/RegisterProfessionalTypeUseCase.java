package com.migracion.rangel.application.professionaltype.usecase;
import com.migracion.rangel.domain.professionaltype.port.repository.ProfessionalTypeRepository;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.application.professionaltype.dto.ProfessionalTypeResponse;
import com.migracion.rangel.application.professionaltype.exception.ProfessionalTypeNotFoundApplicationException;
import com.migracion.rangel.application.professionaltype.exception.DuplicateProfessionalTypeApplicationException;
import com.migracion.rangel.application.professionaltype.command.RegisterProfessionalTypeCommand;
import com.migracion.rangel.domain.professionaltype.model.aggregate.ProfessionalType;
public class RegisterProfessionalTypeUseCase {
    private final ProfessionalTypeRepository repository;
    public RegisterProfessionalTypeUseCase(ProfessionalTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ProfessionalTypeResponse execute(RegisterProfessionalTypeCommand command) {
        var aggregate = ProfessionalType.register(command.name());
        if (repository.existsByName(command.name())) { throw new DuplicateProfessionalTypeApplicationException(); }
        return ProfessionalTypeResponse.from(repository.save(aggregate));
    }
}
