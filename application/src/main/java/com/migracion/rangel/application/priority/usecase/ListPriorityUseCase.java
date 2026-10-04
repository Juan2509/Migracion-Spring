package com.migracion.rangel.application.priority.usecase;
import com.migracion.rangel.domain.priority.port.repository.PriorityRepository;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
import com.migracion.rangel.application.priority.dto.PriorityResponse;
import com.migracion.rangel.application.priority.exception.PriorityNotFoundApplicationException;
import java.util.List;
public class ListPriorityUseCase {
    private final PriorityRepository repository;
    public ListPriorityUseCase(PriorityRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<PriorityResponse> execute() { return repository.findAll().stream().map(PriorityResponse::from).toList(); }
}
