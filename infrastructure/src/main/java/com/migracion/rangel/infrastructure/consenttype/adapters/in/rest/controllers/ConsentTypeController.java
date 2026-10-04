package com.migracion.rangel.infrastructure.consenttype.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.consenttype.command.RegisterConsentTypeCommand;
import com.migracion.rangel.application.consenttype.command.UpdateConsentTypeCommand;
import com.migracion.rangel.application.consenttype.dto.ConsentTypeResponse;
import com.migracion.rangel.application.consenttype.usecase.RegisterConsentTypeUseCase;
import com.migracion.rangel.application.consenttype.usecase.GetConsentTypeByIdUseCase;
import com.migracion.rangel.application.consenttype.usecase.ListConsentTypeUseCase;
import com.migracion.rangel.application.consenttype.usecase.UpdateConsentTypeUseCase;
import com.migracion.rangel.application.consenttype.usecase.DeleteConsentTypeUseCase;
import com.migracion.rangel.domain.consenttype.model.valueobject.ConsentTypeId;
import com.migracion.rangel.infrastructure.consenttype.adapters.in.rest.dtos.CreateConsentTypeRequest;
import com.migracion.rangel.infrastructure.consenttype.adapters.in.rest.dtos.UpdateConsentTypeRequest;
@RestController
@RequestMapping("/api/consent-types")
public class ConsentTypeController {
    private final RegisterConsentTypeUseCase register;
    private final GetConsentTypeByIdUseCase get;
    private final ListConsentTypeUseCase list;
    private final UpdateConsentTypeUseCase update;
    private final DeleteConsentTypeUseCase delete;
    public ConsentTypeController(RegisterConsentTypeUseCase register, GetConsentTypeByIdUseCase get,
            ListConsentTypeUseCase list, UpdateConsentTypeUseCase update, DeleteConsentTypeUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<ConsentTypeResponse> create(@Valid @RequestBody CreateConsentTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterConsentTypeCommand(request.code(), request.name(), request.active(), request.description())));
    }
    @GetMapping
    public List<ConsentTypeResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public ConsentTypeResponse findById(@PathVariable("id") UUID id) { return get.execute(new ConsentTypeId(id)); }
    @PutMapping("/{id}")
    public ConsentTypeResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateConsentTypeRequest request) {
        return update.execute(new UpdateConsentTypeCommand(new ConsentTypeId(id), request.code(), request.name(), request.active(), request.description()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new ConsentTypeId(id));
        return ResponseEntity.noContent().build();
    }
}

