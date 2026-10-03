package com.migracion.rangel.application.professional.usecase;
import com.migracion.rangel.domain.professional.port.repository.ProfessionalRepository;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.application.professional.dto.ProfessionalResponse;
import com.migracion.rangel.application.professional.exception.ProfessionalNotFoundApplicationException;


public class GetProfessionalByIdUseCase {
    private final ProfessionalRepository repository;

    public GetProfessionalByIdUseCase(ProfessionalRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public ProfessionalResponse execute(ProfessionalId id) { return ProfessionalResponse.from(repository.findById(id).orElseThrow(() -> new ProfessionalNotFoundApplicationException(id))); }
}
