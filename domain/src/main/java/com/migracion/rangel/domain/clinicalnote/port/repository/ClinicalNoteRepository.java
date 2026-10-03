package com.migracion.rangel.domain.clinicalnote.port.repository;
import java.util.List;
import java.util.Optional;
import com.migracion.rangel.domain.clinicalnote.model.aggregate.ClinicalNote;
import com.migracion.rangel.domain.clinicalnote.model.valueobject.ClinicalNoteId;
public interface ClinicalNoteRepository {
    ClinicalNote save(ClinicalNote aggregate);
    Optional<ClinicalNote> findById(ClinicalNoteId id);
    List<ClinicalNote> findAll();
    void delete(ClinicalNote aggregate);
}

