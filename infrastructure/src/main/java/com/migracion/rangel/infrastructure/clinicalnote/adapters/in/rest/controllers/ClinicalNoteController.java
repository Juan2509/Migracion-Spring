package com.migracion.rangel.infrastructure.clinicalnote.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.clinicalnote.model.valueobject.ClinicalNoteId;
import com.migracion.rangel.application.clinicalnote.command.*;
import com.migracion.rangel.application.clinicalnote.dto.ClinicalNoteResponse;
import com.migracion.rangel.application.clinicalnote.usecase.*;
import com.migracion.rangel.infrastructure.clinicalnote.adapters.in.rest.dtos.*;
@RestController
@RequestMapping("/api/clinical-notes")
public class ClinicalNoteController {
    private final RegisterClinicalNoteUseCase register;
    private final GetClinicalNoteByIdUseCase get;
    private final ListClinicalNoteUseCase list;
    private final UpdateClinicalNoteUseCase update;
    private final DeleteClinicalNoteUseCase delete;
    public ClinicalNoteController(RegisterClinicalNoteUseCase register, GetClinicalNoteByIdUseCase get,
            ListClinicalNoteUseCase list, UpdateClinicalNoteUseCase update, DeleteClinicalNoteUseCase delete) {
        this.register = register; this.get = get; this.list = list; this.update = update; this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<ClinicalNoteResponse> create(@Valid @RequestBody CreateClinicalNoteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterClinicalNoteCommand(new EncounterId(request.encounterId()), request.subjective(), request.objective(), request.assessment(), request.plan(), request.additionalNotes(), request.signedAt(), new ProfessionalId(request.professionalId()))));
    }
    @GetMapping
    public List<ClinicalNoteResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public ClinicalNoteResponse findById(@PathVariable("id") UUID id) { return get.execute(new ClinicalNoteId(id)); }
    @PutMapping("/{id}")
    public ClinicalNoteResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateClinicalNoteRequest request) {
        return update.execute(new UpdateClinicalNoteCommand(new ClinicalNoteId(id), new EncounterId(request.encounterId()), request.subjective(), request.objective(), request.assessment(), request.plan(), request.additionalNotes(), request.signedAt(), new ProfessionalId(request.professionalId())));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new ClinicalNoteId(id));
        return ResponseEntity.noContent().build();
    }
}

