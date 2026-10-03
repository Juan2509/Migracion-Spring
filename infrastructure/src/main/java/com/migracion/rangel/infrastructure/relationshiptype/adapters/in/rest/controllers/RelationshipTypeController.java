package com.migracion.rangel.infrastructure.relationshiptype.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.relationshiptype.command.RegisterRelationshipTypeCommand;
import com.migracion.rangel.application.relationshiptype.command.UpdateRelationshipTypeCommand;
import com.migracion.rangel.application.relationshiptype.dto.RelationshipTypeResponse;
import com.migracion.rangel.application.relationshiptype.usecase.RegisterRelationshipTypeUseCase;
import com.migracion.rangel.application.relationshiptype.usecase.GetRelationshipTypeByIdUseCase;
import com.migracion.rangel.application.relationshiptype.usecase.ListRelationshipTypeUseCase;
import com.migracion.rangel.application.relationshiptype.usecase.UpdateRelationshipTypeUseCase;
import com.migracion.rangel.application.relationshiptype.usecase.DeleteRelationshipTypeUseCase;
import com.migracion.rangel.domain.relationshiptype.model.valueobject.RelationshipTypeId;
import com.migracion.rangel.infrastructure.relationshiptype.adapters.in.rest.dtos.CreateRelationshipTypeRequest;
import com.migracion.rangel.infrastructure.relationshiptype.adapters.in.rest.dtos.UpdateRelationshipTypeRequest;
@RestController
@RequestMapping("/api/relationship-types")
public class RelationshipTypeController {
    private final RegisterRelationshipTypeUseCase register;
    private final GetRelationshipTypeByIdUseCase get;
    private final ListRelationshipTypeUseCase list;
    private final UpdateRelationshipTypeUseCase update;
    private final DeleteRelationshipTypeUseCase delete;
    public RelationshipTypeController(RegisterRelationshipTypeUseCase register, GetRelationshipTypeByIdUseCase get,
            ListRelationshipTypeUseCase list, UpdateRelationshipTypeUseCase update, DeleteRelationshipTypeUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<RelationshipTypeResponse> create(@Valid @RequestBody CreateRelationshipTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterRelationshipTypeCommand(request.description())));
    }
    @GetMapping
    public List<RelationshipTypeResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public RelationshipTypeResponse findById(@PathVariable("id") UUID id) { return get.execute(new RelationshipTypeId(id)); }
    @PutMapping("/{id}")
    public RelationshipTypeResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateRelationshipTypeRequest request) {
        return update.execute(new UpdateRelationshipTypeCommand(new RelationshipTypeId(id), request.description()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new RelationshipTypeId(id));
        return ResponseEntity.noContent().build();
    }
}
