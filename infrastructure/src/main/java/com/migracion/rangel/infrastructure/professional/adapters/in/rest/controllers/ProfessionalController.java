package com.migracion.rangel.infrastructure.professional.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.professional.command.RegisterProfessionalCommand;
import com.migracion.rangel.application.professional.command.UpdateProfessionalCommand;
import com.migracion.rangel.application.professional.dto.ProfessionalResponse;
import com.migracion.rangel.application.professional.usecase.RegisterProfessionalUseCase;
import com.migracion.rangel.application.professional.usecase.GetProfessionalByIdUseCase;
import com.migracion.rangel.application.professional.usecase.ListProfessionalUseCase;
import com.migracion.rangel.application.professional.usecase.UpdateProfessionalUseCase;
import com.migracion.rangel.application.professional.usecase.DeleteProfessionalUseCase;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.domain.professionaltype.model.valueobject.ProfessionalTypeId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.infrastructure.professional.adapters.in.rest.dtos.CreateProfessionalRequest;
import com.migracion.rangel.infrastructure.professional.adapters.in.rest.dtos.UpdateProfessionalRequest;

@RestController
@RequestMapping("/api/professionals")
public class ProfessionalController {
    private final RegisterProfessionalUseCase register;
    private final GetProfessionalByIdUseCase get;
    private final ListProfessionalUseCase list;
    private final UpdateProfessionalUseCase update;
    private final DeleteProfessionalUseCase delete;
    public ProfessionalController(RegisterProfessionalUseCase register, GetProfessionalByIdUseCase get,
            ListProfessionalUseCase list, UpdateProfessionalUseCase update, DeleteProfessionalUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<ProfessionalResponse> create(@Valid @RequestBody CreateProfessionalRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterProfessionalCommand(new DocumentTypeId(request.documentTypeId()), request.documentNumber(), request.firstName(), request.lastName(), new ProfessionalTypeId(request.professionalType()), request.licenseNumber(), request.active(), new CityMunicipalityId(request.cityId()))));
    }
    @GetMapping
    public List<ProfessionalResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public ProfessionalResponse findById(@PathVariable("id") UUID id) { return get.execute(new ProfessionalId(id)); }
    @PutMapping("/{id}")
    public ProfessionalResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateProfessionalRequest request) {
        return update.execute(new UpdateProfessionalCommand(new ProfessionalId(id), new DocumentTypeId(request.documentTypeId()), request.documentNumber(), request.firstName(), request.lastName(), new ProfessionalTypeId(request.professionalType()), request.licenseNumber(), request.active(), new CityMunicipalityId(request.cityId())));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new ProfessionalId(id));
        return ResponseEntity.noContent().build();
    }
}
