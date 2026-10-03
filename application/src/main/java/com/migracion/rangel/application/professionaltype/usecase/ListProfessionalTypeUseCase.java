package com.migracion.rangel.application.professionaltype.usecase;
import com.migracion.rangel.domain.professionaltype.port.repository.ProfessionalTypeRepository;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.application.professionaltype.dto.ProfessionalTypeResponse;
import com.migracion.rangel.application.professionaltype.exception.ProfessionalTypeNotFoundApplicationException;
import com.migracion.rangel.application.professionaltype.exception.DuplicateProfessionalTypeApplicationException;
import java.util.List;
public class ListProfessionalTypeUseCase {
    private final ProfessionalTypeRepository repository;
    public ListProfessionalTypeUseCase(ProfessionalTypeRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<ProfessionalTypeResponse> execute() { return repository.findAll().stream().map(ProfessionalTypeResponse::from).toList(); }
}
