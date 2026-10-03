package com.migracion.rangel.application.mentalstatusexam.usecase;
import java.util.List;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.mentalstatusexam.port.repository.MentalStatusExamRepository;
import com.migracion.rangel.domain.mentalstatusexam.model.valueobject.MentalStatusExamId;
import com.migracion.rangel.domain.mentalstatusexam.event.MentalStatusExamDeletedEvent;
import com.migracion.rangel.application.mentalstatusexam.dto.MentalStatusExamResponse;
import com.migracion.rangel.application.mentalstatusexam.exception.MentalStatusExamNotFoundApplicationException;
public class DeleteMentalStatusExamUseCase {
    private final MentalStatusExamRepository repository;
    public DeleteMentalStatusExamUseCase(MentalStatusExamRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public MentalStatusExamDeletedEvent execute(MentalStatusExamId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new MentalStatusExamNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new MentalStatusExamDeletedEvent(id, LocalDateTime.now());
    }
}

