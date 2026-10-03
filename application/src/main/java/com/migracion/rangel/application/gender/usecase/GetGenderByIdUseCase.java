package com.migracion.rangel.application.gender.usecase;
import com.migracion.rangel.domain.gender.port.repository.GenderRepository;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
import com.migracion.rangel.application.gender.dto.GenderResponse;
import com.migracion.rangel.application.gender.exception.GenderNotFoundApplicationException;
import com.migracion.rangel.application.gender.exception.DuplicateGenderApplicationException;

public class GetGenderByIdUseCase {
    private final GenderRepository repository;
    public GetGenderByIdUseCase(GenderRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public GenderResponse execute(GenderId id) { return GenderResponse.from(repository.findById(id).orElseThrow(() -> new GenderNotFoundApplicationException(id))); }
}
