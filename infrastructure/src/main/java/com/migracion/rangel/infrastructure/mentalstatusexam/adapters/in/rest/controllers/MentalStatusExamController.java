package com.migracion.rangel.infrastructure.mentalstatusexam.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.domain.encounter.model.valueobject.EncounterId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.mentalstatusexam.model.valueobject.MentalStatusExamId;
import com.migracion.rangel.application.mentalstatusexam.command.*;
import com.migracion.rangel.application.mentalstatusexam.dto.MentalStatusExamResponse;
import com.migracion.rangel.application.mentalstatusexam.usecase.*;
import com.migracion.rangel.infrastructure.mentalstatusexam.adapters.in.rest.dtos.*;
@RestController
@RequestMapping("/api/mental-status-exams")
public class MentalStatusExamController {
    private final RegisterMentalStatusExamUseCase register;
    private final GetMentalStatusExamByIdUseCase get;
    private final ListMentalStatusExamUseCase list;
    private final UpdateMentalStatusExamUseCase update;
    private final DeleteMentalStatusExamUseCase delete;
    public MentalStatusExamController(RegisterMentalStatusExamUseCase register, GetMentalStatusExamByIdUseCase get,
            ListMentalStatusExamUseCase list, UpdateMentalStatusExamUseCase update, DeleteMentalStatusExamUseCase delete) {
        this.register = register; this.get = get; this.list = list; this.update = update; this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<MentalStatusExamResponse> create(@Valid @RequestBody CreateMentalStatusExamRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterMentalStatusExamCommand(new EncounterId(request.encounterId()), request.appearance(), request.behavior(), request.attitude(), request.consciousness(), request.orientation(), request.attention(), request.memory(), request.speech(), request.mood(), request.affect(), request.thoughtProcess(), request.thoughtContent(), request.perception(), request.judgment(), request.insight(), request.psychomotorActivity(), request.observations(), new ProfessionalId(request.createdBy()))));
    }
    @GetMapping
    public List<MentalStatusExamResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public MentalStatusExamResponse findById(@PathVariable("id") UUID id) { return get.execute(new MentalStatusExamId(id)); }
    @PutMapping("/{id}")
    public MentalStatusExamResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateMentalStatusExamRequest request) {
        return update.execute(new UpdateMentalStatusExamCommand(new MentalStatusExamId(id), new EncounterId(request.encounterId()), request.appearance(), request.behavior(), request.attitude(), request.consciousness(), request.orientation(), request.attention(), request.memory(), request.speech(), request.mood(), request.affect(), request.thoughtProcess(), request.thoughtContent(), request.perception(), request.judgment(), request.insight(), request.psychomotorActivity(), request.observations()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new MentalStatusExamId(id)); return ResponseEntity.noContent().build();
    }
}
