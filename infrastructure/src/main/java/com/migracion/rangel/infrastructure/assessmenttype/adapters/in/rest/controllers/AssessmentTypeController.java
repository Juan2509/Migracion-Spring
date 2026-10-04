package com.migracion.rangel.infrastructure.assessmenttype.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.assessmenttype.command.RegisterAssessmentTypeCommand;
import com.migracion.rangel.application.assessmenttype.command.UpdateAssessmentTypeCommand;
import com.migracion.rangel.application.assessmenttype.dto.AssessmentTypeResponse;
import com.migracion.rangel.application.assessmenttype.usecase.RegisterAssessmentTypeUseCase;
import com.migracion.rangel.application.assessmenttype.usecase.GetAssessmentTypeByIdUseCase;
import com.migracion.rangel.application.assessmenttype.usecase.ListAssessmentTypeUseCase;
import com.migracion.rangel.application.assessmenttype.usecase.UpdateAssessmentTypeUseCase;
import com.migracion.rangel.application.assessmenttype.usecase.DeleteAssessmentTypeUseCase;
import com.migracion.rangel.domain.assessmenttype.model.valueobject.AssessmentTypeId;
import com.migracion.rangel.infrastructure.assessmenttype.adapters.in.rest.dtos.CreateAssessmentTypeRequest;
import com.migracion.rangel.infrastructure.assessmenttype.adapters.in.rest.dtos.UpdateAssessmentTypeRequest;
@RestController
@RequestMapping("/api/assessment-types")
public class AssessmentTypeController {
    private final RegisterAssessmentTypeUseCase register;
    private final GetAssessmentTypeByIdUseCase get;
    private final ListAssessmentTypeUseCase list;
    private final UpdateAssessmentTypeUseCase update;
    private final DeleteAssessmentTypeUseCase delete;
    public AssessmentTypeController(RegisterAssessmentTypeUseCase register, GetAssessmentTypeByIdUseCase get,
            ListAssessmentTypeUseCase list, UpdateAssessmentTypeUseCase update, DeleteAssessmentTypeUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<AssessmentTypeResponse> create(@Valid @RequestBody CreateAssessmentTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterAssessmentTypeCommand(request.code(), request.name(), request.active(), request.description())));
    }
    @GetMapping
    public List<AssessmentTypeResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public AssessmentTypeResponse findById(@PathVariable("id") UUID id) { return get.execute(new AssessmentTypeId(id)); }
    @PutMapping("/{id}")
    public AssessmentTypeResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateAssessmentTypeRequest request) {
        return update.execute(new UpdateAssessmentTypeCommand(new AssessmentTypeId(id), request.code(), request.name(), request.active(), request.description()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new AssessmentTypeId(id));
        return ResponseEntity.noContent().build();
    }
}

