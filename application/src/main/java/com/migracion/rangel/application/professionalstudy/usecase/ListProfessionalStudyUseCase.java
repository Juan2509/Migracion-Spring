package com.migracion.rangel.application.professionalstudy.usecase;
import com.migracion.rangel.domain.professionalstudy.port.repository.ProfessionalStudyRepository;
import com.migracion.rangel.domain.professionalstudy.model.valueobject.ProfessionalStudyId;
import com.migracion.rangel.application.professionalstudy.dto.ProfessionalStudyResponse;
import com.migracion.rangel.application.professionalstudy.exception.ProfessionalStudyNotFoundApplicationException;

import java.util.List;
public class ListProfessionalStudyUseCase {
    private final ProfessionalStudyRepository repository;

    public ListProfessionalStudyUseCase(ProfessionalStudyRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public List<ProfessionalStudyResponse> execute() { return repository.findAll().stream().map(ProfessionalStudyResponse::from).toList(); }
}
