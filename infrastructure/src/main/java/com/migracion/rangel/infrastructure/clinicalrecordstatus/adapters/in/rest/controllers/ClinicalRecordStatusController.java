package com.migracion.rangel.infrastructure.clinicalrecordstatus.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.migracion.rangel.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import com.migracion.rangel.application.clinicalrecordstatus.command.*;
import com.migracion.rangel.application.clinicalrecordstatus.dto.ClinicalRecordStatusResponse;
import com.migracion.rangel.application.clinicalrecordstatus.usecase.*;
import com.migracion.rangel.infrastructure.clinicalrecordstatus.adapters.in.rest.dtos.*;
@RestController
@RequestMapping("/api/clinical-record-statuses")
public class ClinicalRecordStatusController {
    private final RegisterClinicalRecordStatusUseCase register;
    private final GetClinicalRecordStatusByIdUseCase get;
    private final ListClinicalRecordStatusUseCase list;
    private final UpdateClinicalRecordStatusUseCase update;
    private final DeleteClinicalRecordStatusUseCase delete;
    public ClinicalRecordStatusController(RegisterClinicalRecordStatusUseCase register, GetClinicalRecordStatusByIdUseCase get,
            ListClinicalRecordStatusUseCase list, UpdateClinicalRecordStatusUseCase update, DeleteClinicalRecordStatusUseCase delete) {
        this.register = register; this.get = get; this.list = list; this.update = update; this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<ClinicalRecordStatusResponse> create(@Valid @RequestBody CreateClinicalRecordStatusRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterClinicalRecordStatusCommand(request.code(), request.name())));
    }
    @GetMapping
    public List<ClinicalRecordStatusResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public ClinicalRecordStatusResponse findById(@PathVariable("id") UUID id) { return get.execute(new ClinicalRecordStatusId(id)); }
    @PutMapping("/{id}")
    public ClinicalRecordStatusResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateClinicalRecordStatusRequest request) {
        return update.execute(new UpdateClinicalRecordStatusCommand(new ClinicalRecordStatusId(id), request.code(), request.name()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new ClinicalRecordStatusId(id));
        return ResponseEntity.noContent().build();
    }
}
