package com.migracion.rangel.infrastructure.priority.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.priority.command.RegisterPriorityCommand;
import com.migracion.rangel.application.priority.command.UpdatePriorityCommand;
import com.migracion.rangel.application.priority.dto.PriorityResponse;
import com.migracion.rangel.application.priority.usecase.RegisterPriorityUseCase;
import com.migracion.rangel.application.priority.usecase.GetPriorityByIdUseCase;
import com.migracion.rangel.application.priority.usecase.ListPriorityUseCase;
import com.migracion.rangel.application.priority.usecase.UpdatePriorityUseCase;
import com.migracion.rangel.application.priority.usecase.DeletePriorityUseCase;
import com.migracion.rangel.domain.priority.model.valueobject.PriorityId;
import com.migracion.rangel.infrastructure.priority.adapters.in.rest.dtos.CreatePriorityRequest;
import com.migracion.rangel.infrastructure.priority.adapters.in.rest.dtos.UpdatePriorityRequest;
@RestController
@RequestMapping("/api/priorities")
public class PriorityController {
    private final RegisterPriorityUseCase register;
    private final GetPriorityByIdUseCase get;
    private final ListPriorityUseCase list;
    private final UpdatePriorityUseCase update;
    private final DeletePriorityUseCase delete;
    public PriorityController(RegisterPriorityUseCase register, GetPriorityByIdUseCase get,
            ListPriorityUseCase list, UpdatePriorityUseCase update, DeletePriorityUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<PriorityResponse> create(@Valid @RequestBody CreatePriorityRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterPriorityCommand(request.namePriority())));
    }
    @GetMapping
    public List<PriorityResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public PriorityResponse findById(@PathVariable("id") UUID id) { return get.execute(new PriorityId(id)); }
    @PutMapping("/{id}")
    public PriorityResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdatePriorityRequest request) {
        return update.execute(new UpdatePriorityCommand(new PriorityId(id), request.namePriority()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new PriorityId(id));
        return ResponseEntity.noContent().build();
    }
}
