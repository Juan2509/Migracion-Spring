package com.migracion.rangel.infrastructure.gender.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.gender.command.RegisterGenderCommand;
import com.migracion.rangel.application.gender.command.UpdateGenderCommand;
import com.migracion.rangel.application.gender.dto.GenderResponse;
import com.migracion.rangel.application.gender.usecase.RegisterGenderUseCase;
import com.migracion.rangel.application.gender.usecase.GetGenderByIdUseCase;
import com.migracion.rangel.application.gender.usecase.ListGenderUseCase;
import com.migracion.rangel.application.gender.usecase.UpdateGenderUseCase;
import com.migracion.rangel.application.gender.usecase.DeleteGenderUseCase;
import com.migracion.rangel.domain.gender.model.valueobject.GenderId;
import com.migracion.rangel.infrastructure.gender.adapters.in.rest.dtos.CreateGenderRequest;
import com.migracion.rangel.infrastructure.gender.adapters.in.rest.dtos.UpdateGenderRequest;
@RestController
@RequestMapping("/api/genders")
public class GenderController {
    private final RegisterGenderUseCase register;
    private final GetGenderByIdUseCase get;
    private final ListGenderUseCase list;
    private final UpdateGenderUseCase update;
    private final DeleteGenderUseCase delete;
    public GenderController(RegisterGenderUseCase register, GetGenderByIdUseCase get,
            ListGenderUseCase list, UpdateGenderUseCase update, DeleteGenderUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<GenderResponse> create(@Valid @RequestBody CreateGenderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterGenderCommand(request.description())));
    }
    @GetMapping
    public List<GenderResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public GenderResponse findById(@PathVariable("id") UUID id) { return get.execute(new GenderId(id)); }
    @PutMapping("/{id}")
    public GenderResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateGenderRequest request) {
        return update.execute(new UpdateGenderCommand(new GenderId(id), request.description()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new GenderId(id));
        return ResponseEntity.noContent().build();
    }
}
