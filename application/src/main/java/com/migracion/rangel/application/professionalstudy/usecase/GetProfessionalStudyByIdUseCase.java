package com.migracion.rangel.application.professionalstudy.usecase;
import com.migracion.rangel.domain.professionalstudy.port.repository.ProfessionalStudyRepository;
import com.migracion.rangel.domain.professionalstudy.model.valueobject.ProfessionalStudyId;
import com.migracion.rangel.application.professionalstudy.dto.ProfessionalStudyResponse;
import com.migracion.rangel.application.professionalstudy.exception.ProfessionalStudyNotFoundApplicationException;


public class GetProfessionalStudyByIdUseCase {
    private final ProfessionalStudyRepository repository;

    public GetProfessionalStudyByIdUseCase(ProfessionalStudyRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository);

    }
    public ProfessionalStudyResponse execute(ProfessionalStudyId id) { return ProfessionalStudyResponse.from(repository.findById(id).orElseThrow(() -> new ProfessionalStudyNotFoundApplicationException(id))); }
}
