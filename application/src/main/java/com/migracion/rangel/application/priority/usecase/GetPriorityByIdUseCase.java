package com.migracion.rangel.application.priority.usecase;
import com.migracion.rangel.domain.priority.port.repository.PriorityRepository;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
import com.migracion.rangel.application.priority.dto.PriorityResponse;
import com.migracion.rangel.application.priority.exception.PriorityNotFoundApplicationException;

public class GetPriorityByIdUseCase {
    private final PriorityRepository repository;
    public GetPriorityByIdUseCase(PriorityRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public PriorityResponse execute(PriorityId id) { return PriorityResponse.from(repository.findById(id).orElseThrow(() -> new PriorityNotFoundApplicationException(id))); }
}
