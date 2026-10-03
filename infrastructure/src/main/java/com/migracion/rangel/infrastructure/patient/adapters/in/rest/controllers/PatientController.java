package com.migracion.rangel.infrastructure.patient.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;

import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.application.patient.command.*;
import com.migracion.rangel.application.patient.dto.PatientResponse;
import com.migracion.rangel.application.patient.usecase.*;
import com.migracion.rangel.infrastructure.patient.adapters.in.rest.dtos.*;
@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final RegisterPatientUseCase register;
    private final GetPatientByIdUseCase get;
    private final ListPatientUseCase list;
    private final UpdatePatientUseCase update;
    private final DeletePatientUseCase delete;
    public PatientController(RegisterPatientUseCase register, GetPatientByIdUseCase get,
            ListPatientUseCase list, UpdatePatientUseCase update, DeletePatientUseCase delete) {
        this.register = register; this.get = get; this.list = list; this.update = update; this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<PatientResponse> create(@Valid @RequestBody CreatePatientRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterPatientCommand(
                new DocumentTypeId(request.documentTypeId()), request.documentNumber(), request.firstName(), request.middleName(), request.lastName(), request.secondLastName(), request.birthDate(), new GenderId(request.biologicalSexId()), new GenderId(request.genderIdentity()), request.email(), request.phone(), request.address(), request.active(), new CityMunicipalityId(request.cityId()), request.createdBy() == null ? null : new ProfessionalId(request.createdBy()))));
    }
    @GetMapping
    public List<PatientResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public PatientResponse findById(@PathVariable("id") UUID id) { return get.execute(new PatientId(id)); }
    @PutMapping("/{id}")
    public PatientResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdatePatientRequest request) {
        return update.execute(new UpdatePatientCommand(new PatientId(id),
                new DocumentTypeId(request.documentTypeId()), request.documentNumber(), request.firstName(), request.middleName(), request.lastName(), request.secondLastName(), request.birthDate(), new GenderId(request.biologicalSexId()), new GenderId(request.genderIdentity()), request.email(), request.phone(), request.address(), request.active(), new CityMunicipalityId(request.cityId()), request.updatedBy() == null ? null : new ProfessionalId(request.updatedBy())));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new PatientId(id));
        return ResponseEntity.noContent().build();
    }
}
