package com.migracion.rangel.infrastructure.clinicalrecord.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.clinicalrecordstatus.model.valueobject.ClinicalRecordStatusId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.migracion.rangel.application.clinicalrecord.command.*;
import com.migracion.rangel.application.clinicalrecord.dto.ClinicalRecordResponse;
import com.migracion.rangel.application.clinicalrecord.usecase.*;
import com.migracion.rangel.infrastructure.clinicalrecord.adapters.in.rest.dtos.*;
@RestController
@RequestMapping("/api/clinical-records")
public class ClinicalRecordController {
    private final RegisterClinicalRecordUseCase register;
    private final GetClinicalRecordByIdUseCase get;
    private final ListClinicalRecordUseCase list;
    private final UpdateClinicalRecordUseCase update;
    private final DeleteClinicalRecordUseCase delete;
    public ClinicalRecordController(RegisterClinicalRecordUseCase register, GetClinicalRecordByIdUseCase get,
            ListClinicalRecordUseCase list, UpdateClinicalRecordUseCase update, DeleteClinicalRecordUseCase delete) {
        this.register = register; this.get = get; this.list = list; this.update = update; this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<ClinicalRecordResponse> create(@Valid @RequestBody CreateClinicalRecordRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterClinicalRecordCommand(new PatientId(request.patientId()), request.creationDate(), request.recordNumber(), request.openedAt(), request.closedAt(), new ClinicalRecordStatusId(request.statusId()), new ProfessionalId(request.createdBy()))));
    }
    @GetMapping
    public List<ClinicalRecordResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public ClinicalRecordResponse findById(@PathVariable("id") UUID id) { return get.execute(new ClinicalRecordId(id)); }
    @PutMapping("/{id}")
    public ClinicalRecordResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateClinicalRecordRequest request) {
        return update.execute(new UpdateClinicalRecordCommand(new ClinicalRecordId(id), new PatientId(request.patientId()), request.creationDate(), request.recordNumber(), request.openedAt(), request.closedAt(), new ClinicalRecordStatusId(request.statusId())));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new ClinicalRecordId(id));
        return ResponseEntity.noContent().build();
    }
}
