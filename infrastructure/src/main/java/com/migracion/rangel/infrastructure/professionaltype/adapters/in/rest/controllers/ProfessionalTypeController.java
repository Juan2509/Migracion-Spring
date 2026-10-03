package com.migracion.rangel.infrastructure.professionaltype.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.professionaltype.command.RegisterProfessionalTypeCommand;
import com.migracion.rangel.application.professionaltype.command.UpdateProfessionalTypeCommand;
import com.migracion.rangel.application.professionaltype.dto.ProfessionalTypeResponse;
import com.migracion.rangel.application.professionaltype.usecase.RegisterProfessionalTypeUseCase;
import com.migracion.rangel.application.professionaltype.usecase.GetProfessionalTypeByIdUseCase;
import com.migracion.rangel.application.professionaltype.usecase.ListProfessionalTypeUseCase;
import com.migracion.rangel.application.professionaltype.usecase.UpdateProfessionalTypeUseCase;
import com.migracion.rangel.application.professionaltype.usecase.DeleteProfessionalTypeUseCase;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.infrastructure.professionaltype.adapters.in.rest.dtos.CreateProfessionalTypeRequest;
import com.migracion.rangel.infrastructure.professionaltype.adapters.in.rest.dtos.UpdateProfessionalTypeRequest;
@RestController
@RequestMapping("/api/professional-types")
public class ProfessionalTypeController {
    private final RegisterProfessionalTypeUseCase register;
    private final GetProfessionalTypeByIdUseCase get;
    private final ListProfessionalTypeUseCase list;
    private final UpdateProfessionalTypeUseCase update;
    private final DeleteProfessionalTypeUseCase delete;
    public ProfessionalTypeController(RegisterProfessionalTypeUseCase register, GetProfessionalTypeByIdUseCase get,
            ListProfessionalTypeUseCase list, UpdateProfessionalTypeUseCase update, DeleteProfessionalTypeUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<ProfessionalTypeResponse> create(@Valid @RequestBody CreateProfessionalTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterProfessionalTypeCommand(request.name())));
    }
    @GetMapping
    public List<ProfessionalTypeResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public ProfessionalTypeResponse findById(@PathVariable("id") UUID id) { return get.execute(new ProfessionalTypeId(id)); }
    @PutMapping("/{id}")
    public ProfessionalTypeResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateProfessionalTypeRequest request) {
        return update.execute(new UpdateProfessionalTypeCommand(new ProfessionalTypeId(id), request.name()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new ProfessionalTypeId(id));
        return ResponseEntity.noContent().build();
    }
}
