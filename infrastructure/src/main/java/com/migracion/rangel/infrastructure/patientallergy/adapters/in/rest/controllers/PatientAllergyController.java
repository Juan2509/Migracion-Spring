package com.migracion.rangel.infrastructure.patientallergy.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.patientallergy.model.valueobject.PatientAllergyId;
import com.migracion.rangel.application.patientallergy.command.*;
import com.migracion.rangel.application.patientallergy.dto.PatientAllergyResponse;
import com.migracion.rangel.application.patientallergy.usecase.*;
import com.migracion.rangel.infrastructure.patientallergy.adapters.in.rest.dtos.*;
@RestController
@RequestMapping("/api/patient-allergies")
public class PatientAllergyController {
    private final RegisterPatientAllergyUseCase register;
    private final GetPatientAllergyByIdUseCase get;
    private final ListPatientAllergyUseCase list;
    private final UpdatePatientAllergyUseCase update;
    private final DeletePatientAllergyUseCase delete;
    public PatientAllergyController(RegisterPatientAllergyUseCase register, GetPatientAllergyByIdUseCase get,
            ListPatientAllergyUseCase list, UpdatePatientAllergyUseCase update, DeletePatientAllergyUseCase delete) {
        this.register = register; this.get = get; this.list = list; this.update = update; this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<PatientAllergyResponse> create(@Valid @RequestBody CreatePatientAllergyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterPatientAllergyCommand(new PatientId(request.patientId()), request.substance(), request.reaction(), request.severity(), request.active(), request.recordedAt(), new ProfessionalId(request.recordedBy()))));
    }
    @GetMapping
    public List<PatientAllergyResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public PatientAllergyResponse findById(@PathVariable("id") UUID id) { return get.execute(new PatientAllergyId(id)); }
    @PutMapping("/{id}")
    public PatientAllergyResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdatePatientAllergyRequest request) {
        return update.execute(new UpdatePatientAllergyCommand(new PatientAllergyId(id), new PatientId(request.patientId()), request.substance(), request.reaction(), request.severity(), request.active(), request.recordedAt(), new ProfessionalId(request.recordedBy())));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new PatientAllergyId(id));
        return ResponseEntity.noContent().build();
    }
}
