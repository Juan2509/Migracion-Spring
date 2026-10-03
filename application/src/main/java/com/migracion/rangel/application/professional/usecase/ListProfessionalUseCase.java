package com.migracion.rangel.application.professional.usecase;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.application.professional.dto.ProfessionalResponse;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;

import java.util.List;
public class ListProfessionalUseCase {
    private final ProfessionalRepository repository;

    public ListProfessionalUseCase(ProfessionalRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public List<ProfessionalResponse> execute() { return repository.findAll().stream().map(ProfessionalResponse::from).toList(); }
}
