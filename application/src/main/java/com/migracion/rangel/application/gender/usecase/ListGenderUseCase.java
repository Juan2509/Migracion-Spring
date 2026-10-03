package com.migracion.rangel.application.gender.usecase;
import com.migracion.rangel.domain.gender.port.repository.GenderRepository;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
import com.migracion.rangel.application.gender.dto.GenderResponse;
import com.migracion.rangel.application.gender.exception.GenderNotFoundApplicationException;
import com.migracion.rangel.application.gender.exception.DuplicateGenderApplicationException;
import java.util.List;
public class ListGenderUseCase {
    private final GenderRepository repository;
    public ListGenderUseCase(GenderRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<GenderResponse> execute() { return repository.findAll().stream().map(GenderResponse::from).toList(); }
}
