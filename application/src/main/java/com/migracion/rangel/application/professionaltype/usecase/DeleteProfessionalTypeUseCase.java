package com.migracion.rangel.application.professionaltype.usecase;
import com.migracion.rangel.domain.professionaltype.port.repository.ProfessionalTypeRepository;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.application.professionaltype.dto.ProfessionalTypeResponse;
import com.migracion.rangel.application.professionaltype.exception.ProfessionalTypeNotFoundApplicationException;
import com.migracion.rangel.application.professionaltype.exception.DuplicateProfessionalTypeApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.professionaltype.event.ProfessionalTypeDeletedEvent;
public class DeleteProfessionalTypeUseCase {
    private final ProfessionalTypeRepository repository;
    public DeleteProfessionalTypeUseCase(ProfessionalTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ProfessionalTypeDeletedEvent execute(ProfessionalTypeId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new ProfessionalTypeNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new ProfessionalTypeDeletedEvent(id, LocalDateTime.now());
    }
}
