package com.migracion.rangel.application.gender.usecase;
import com.migracion.rangel.domain.gender.port.repository.GenderRepository;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
import com.migracion.rangel.application.gender.dto.GenderResponse;
import com.migracion.rangel.application.gender.exception.GenderNotFoundApplicationException;
import com.migracion.rangel.application.gender.exception.DuplicateGenderApplicationException;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.gender.event.GenderDeletedEvent;
public class DeleteGenderUseCase {
    private final GenderRepository repository;
    public DeleteGenderUseCase(GenderRepository repository) { this.repository = java.util.Objects.requireNonNull(repository); }
    public GenderDeletedEvent execute(GenderId id) {
        var aggregate = repository.findById(id).orElseThrow(() -> new GenderNotFoundApplicationException(id));
        repository.delete(aggregate);
        return new GenderDeletedEvent(id, LocalDateTime.now());
    }
}
