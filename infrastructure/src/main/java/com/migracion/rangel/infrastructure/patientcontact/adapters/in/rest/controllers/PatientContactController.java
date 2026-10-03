package com.migracion.rangel.infrastructure.patientcontact.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.domain.patient.model.valueobject.PatientId;
import com.migracion.rangel.domain.relationshiptype.model.valueobject.RelationshipTypeId;
import com.migracion.rangel.domain.patientcontact.model.valueobject.PatientContactId;
import com.migracion.rangel.application.patientcontact.command.*;
import com.migracion.rangel.application.patientcontact.dto.PatientContactResponse;
import com.migracion.rangel.application.patientcontact.usecase.*;
import com.migracion.rangel.infrastructure.patientcontact.adapters.in.rest.dtos.*;
@RestController
@RequestMapping("/api/patient-contacts")
public class PatientContactController {
    private final RegisterPatientContactUseCase register;
    private final GetPatientContactByIdUseCase get;
    private final ListPatientContactUseCase list;
    private final UpdatePatientContactUseCase update;
    private final DeletePatientContactUseCase delete;
    public PatientContactController(RegisterPatientContactUseCase register, GetPatientContactByIdUseCase get,
            ListPatientContactUseCase list, UpdatePatientContactUseCase update, DeletePatientContactUseCase delete) {
        this.register = register; this.get = get; this.list = list; this.update = update; this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<PatientContactResponse> create(@Valid @RequestBody CreatePatientContactRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterPatientContactCommand(new ContactId(request.contactId()), new PatientId(request.patientId()), request.isPrimaryContact(), request.isEmergencyContact(), new RelationshipTypeId(request.relationshipTypeId()))));
    }
    @GetMapping
    public List<PatientContactResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public PatientContactResponse findById(@PathVariable("id") UUID id) { return get.execute(new PatientContactId(id)); }
    @PutMapping("/{id}")
    public PatientContactResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdatePatientContactRequest request) {
        return update.execute(new UpdatePatientContactCommand(new PatientContactId(id), new ContactId(request.contactId()), new PatientId(request.patientId()), request.isPrimaryContact(), request.isEmergencyContact(), new RelationshipTypeId(request.relationshipTypeId())));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new PatientContactId(id));
        return ResponseEntity.noContent().build();
    }
}
