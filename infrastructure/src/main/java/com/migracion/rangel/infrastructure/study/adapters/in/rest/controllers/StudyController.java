package com.migracion.rangel.infrastructure.study.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.study.command.RegisterStudyCommand;
import com.migracion.rangel.application.study.command.UpdateStudyCommand;
import com.migracion.rangel.application.study.dto.StudyResponse;
import com.migracion.rangel.application.study.usecase.RegisterStudyUseCase;
import com.migracion.rangel.application.study.usecase.GetStudyByIdUseCase;
import com.migracion.rangel.application.study.usecase.ListStudyUseCase;
import com.migracion.rangel.application.study.usecase.UpdateStudyUseCase;
import com.migracion.rangel.application.study.usecase.DeleteStudyUseCase;
import com.migracion.rangel.domain.study.model.valueobject.StudyId;
import com.migracion.rangel.infrastructure.study.adapters.in.rest.dtos.CreateStudyRequest;
import com.migracion.rangel.infrastructure.study.adapters.in.rest.dtos.UpdateStudyRequest;
@RestController
@RequestMapping("/api/studies")
public class StudyController {
    private final RegisterStudyUseCase register;
    private final GetStudyByIdUseCase get;
    private final ListStudyUseCase list;
    private final UpdateStudyUseCase update;
    private final DeleteStudyUseCase delete;
    public StudyController(RegisterStudyUseCase register, GetStudyByIdUseCase get,
            ListStudyUseCase list, UpdateStudyUseCase update, DeleteStudyUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<StudyResponse> create(@Valid @RequestBody CreateStudyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterStudyCommand(request.name())));
    }
    @GetMapping
    public List<StudyResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public StudyResponse findById(@PathVariable("id") UUID id) { return get.execute(new StudyId(id)); }
    @PutMapping("/{id}")
    public StudyResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateStudyRequest request) {
        return update.execute(new UpdateStudyCommand(new StudyId(id), request.name()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new StudyId(id));
        return ResponseEntity.noContent().build();
    }
}
