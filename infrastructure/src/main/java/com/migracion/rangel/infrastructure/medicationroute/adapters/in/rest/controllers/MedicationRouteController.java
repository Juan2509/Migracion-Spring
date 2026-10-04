package com.migracion.rangel.infrastructure.medicationroute.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.medicationroute.command.RegisterMedicationRouteCommand;
import com.migracion.rangel.application.medicationroute.command.UpdateMedicationRouteCommand;
import com.migracion.rangel.application.medicationroute.dto.MedicationRouteResponse;
import com.migracion.rangel.application.medicationroute.usecase.RegisterMedicationRouteUseCase;
import com.migracion.rangel.application.medicationroute.usecase.GetMedicationRouteByIdUseCase;
import com.migracion.rangel.application.medicationroute.usecase.ListMedicationRouteUseCase;
import com.migracion.rangel.application.medicationroute.usecase.UpdateMedicationRouteUseCase;
import com.migracion.rangel.application.medicationroute.usecase.DeleteMedicationRouteUseCase;
import com.migracion.rangel.domain.medicationroute.model.valueobject.MedicationRouteId;
import com.migracion.rangel.infrastructure.medicationroute.adapters.in.rest.dtos.CreateMedicationRouteRequest;
import com.migracion.rangel.infrastructure.medicationroute.adapters.in.rest.dtos.UpdateMedicationRouteRequest;
@RestController
@RequestMapping("/api/medication-routes")
public class MedicationRouteController {
    private final RegisterMedicationRouteUseCase register;
    private final GetMedicationRouteByIdUseCase get;
    private final ListMedicationRouteUseCase list;
    private final UpdateMedicationRouteUseCase update;
    private final DeleteMedicationRouteUseCase delete;
    public MedicationRouteController(RegisterMedicationRouteUseCase register, GetMedicationRouteByIdUseCase get,
            ListMedicationRouteUseCase list, UpdateMedicationRouteUseCase update, DeleteMedicationRouteUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<MedicationRouteResponse> create(@Valid @RequestBody CreateMedicationRouteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterMedicationRouteCommand(request.code(), request.name(), request.active())));
    }
    @GetMapping
    public List<MedicationRouteResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public MedicationRouteResponse findById(@PathVariable("id") UUID id) { return get.execute(new MedicationRouteId(id)); }
    @PutMapping("/{id}")
    public MedicationRouteResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateMedicationRouteRequest request) {
        return update.execute(new UpdateMedicationRouteCommand(new MedicationRouteId(id), request.code(), request.name(), request.active()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new MedicationRouteId(id));
        return ResponseEntity.noContent().build();
    }
}

