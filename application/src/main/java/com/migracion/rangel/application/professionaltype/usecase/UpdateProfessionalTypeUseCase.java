package com.migracion.rangel.application.professionaltype.usecase;
import com.migracion.rangel.domain.professionaltype.port.repository.ProfessionalTypeRepository;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.application.professionaltype.dto.ProfessionalTypeResponse;
import com.migracion.rangel.application.professionaltype.exception.ProfessionalTypeNotFoundApplicationException;
import com.migracion.rangel.application.professionaltype.exception.DuplicateProfessionalTypeApplicationException;
import com.migracion.rangel.application.professionaltype.command.UpdateProfessionalTypeCommand;
public class UpdateProfessionalTypeUseCase {
    private final ProfessionalTypeRepository repository;
    public UpdateProfessionalTypeUseCase(ProfessionalTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ProfessionalTypeResponse execute(UpdateProfessionalTypeCommand command) {
        var id = command.id();
        var aggregate = repository.findById(id).orElseThrow(() -> new ProfessionalTypeNotFoundApplicationException(id));
        if (repository.existsByNameAndIdNot(command.name(), id)) { throw new DuplicateProfessionalTypeApplicationException(); }
        aggregate.update(command.name());
        return ProfessionalTypeResponse.from(repository.save(aggregate));
    }
}
