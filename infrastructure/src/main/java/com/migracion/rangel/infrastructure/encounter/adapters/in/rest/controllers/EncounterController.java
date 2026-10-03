package com.migracion.rangel.infrastructure.encounter.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import com.migracion.rangel.domain.clinicalrecord.model.valueobject.ClinicalRecordId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.encountertype.model.valueobject.EncounterTypeId;
import com.migracion.rangel.domain.encountermodality.model.valueobject.EncounterModalityId;
import com.migracion.rangel.domain.encounterstatus.model.valueobject.EncounterStatusId;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.application.encounter.command.*;
import com.migracion.rangel.application.encounter.dto.EncounterResponse;
import com.migracion.rangel.application.encounter.usecase.*;
import com.migracion.rangel.infrastructure.encounter.adapters.in.rest.dtos.*;
@RestController
@RequestMapping("/api/encounters")
public class EncounterController {
    private final RegisterEncounterUseCase register;
    private final GetEncounterByIdUseCase get;
    private final ListEncounterUseCase list;
    private final UpdateEncounterUseCase update;
    private final DeleteEncounterUseCase delete;
    public EncounterController(RegisterEncounterUseCase register, GetEncounterByIdUseCase get,
            ListEncounterUseCase list, UpdateEncounterUseCase update, DeleteEncounterUseCase delete) {
        this.register = register; this.get = get; this.list = list; this.update = update; this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<EncounterResponse> create(@Valid @RequestBody CreateEncounterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterEncounterCommand(new ClinicalRecordId(request.clinicalRecordId()), new ProfessionalId(request.professionalId()), new EncounterTypeId(request.encounterTypeId()), request.startedAt(), request.endedAt(), request.reasonForVisit(), request.currentCondition(), new EncounterModalityId(request.modalityId()), new EncounterStatusId(request.statusId()), new ProfessionalId(request.createdBy()), new ProfessionalId(request.updatedBy()))));
    }
    @GetMapping
    public List<EncounterResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public EncounterResponse findById(@PathVariable("id") UUID id) { return get.execute(new EncounterId(id)); }
    @PutMapping("/{id}")
    public EncounterResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateEncounterRequest request) {
        return update.execute(new UpdateEncounterCommand(new EncounterId(id), new ClinicalRecordId(request.clinicalRecordId()), new ProfessionalId(request.professionalId()), new EncounterTypeId(request.encounterTypeId()), request.startedAt(), request.endedAt(), request.reasonForVisit(), request.currentCondition(), new EncounterModalityId(request.modalityId()), new EncounterStatusId(request.statusId()), new ProfessionalId(request.updatedBy())));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new EncounterId(id));
        return ResponseEntity.noContent().build();
    }
}

