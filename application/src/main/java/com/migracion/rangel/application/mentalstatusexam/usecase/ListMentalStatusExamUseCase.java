package com.migracion.rangel.application.mentalstatusexam.usecase;
import java.util.List;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.mentalstatusexam.port.repository.MentalStatusExamRepository;
import com.migracion.rangel.domain.mentalstatusexam.model.valueobject.MentalStatusExamId;
import com.migracion.rangel.domain.mentalstatusexam.event.MentalStatusExamDeletedEvent;
import com.migracion.rangel.application.mentalstatusexam.dto.MentalStatusExamResponse;
import com.migracion.rangel.application.mentalstatusexam.exception.MentalStatusExamNotFoundApplicationException;
public class ListMentalStatusExamUseCase {
    private final MentalStatusExamRepository repository;
    public ListMentalStatusExamUseCase(MentalStatusExamRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public List<MentalStatusExamResponse> execute() {
        return repository.findAll().stream().map(MentalStatusExamResponse::from).toList();
    }
}

