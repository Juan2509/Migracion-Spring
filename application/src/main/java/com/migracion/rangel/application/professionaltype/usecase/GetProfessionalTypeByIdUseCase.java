package com.migracion.rangel.application.professionaltype.usecase;
import com.migracion.rangel.domain.professionaltype.port.repository.ProfessionalTypeRepository;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.application.professionaltype.dto.ProfessionalTypeResponse;
import com.migracion.rangel.application.professionaltype.exception.ProfessionalTypeNotFoundApplicationException;
import com.migracion.rangel.application.professionaltype.exception.DuplicateProfessionalTypeApplicationException;

public class GetProfessionalTypeByIdUseCase {
    private final ProfessionalTypeRepository repository;
    public GetProfessionalTypeByIdUseCase(ProfessionalTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public ProfessionalTypeResponse execute(ProfessionalTypeId id) { return ProfessionalTypeResponse.from(repository.findById(id).orElseThrow(() -> new ProfessionalTypeNotFoundApplicationException(id))); }
}
