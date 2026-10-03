package com.migracion.rangel.infrastructure.professionalstudy.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.professionalstudy.command.RegisterProfessionalStudyCommand;
import com.migracion.rangel.application.professionalstudy.command.UpdateProfessionalStudyCommand;
import com.migracion.rangel.application.professionalstudy.dto.ProfessionalStudyResponse;
import com.migracion.rangel.application.professionalstudy.usecase.RegisterProfessionalStudyUseCase;
import com.migracion.rangel.application.professionalstudy.usecase.GetProfessionalStudyByIdUseCase;
import com.migracion.rangel.application.professionalstudy.usecase.ListProfessionalStudyUseCase;
import com.migracion.rangel.application.professionalstudy.usecase.UpdateProfessionalStudyUseCase;
import com.migracion.rangel.application.professionalstudy.usecase.DeleteProfessionalStudyUseCase;
import com.migracion.rangel.domain.professionalstudy.model.valueobject.ProfessionalStudyId;
import com.migracion.rangel.domain.study.model.valueobject.StudyId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
import com.migracion.rangel.infrastructure.professionalstudy.adapters.in.rest.dtos.CreateProfessionalStudyRequest;
import com.migracion.rangel.infrastructure.professionalstudy.adapters.in.rest.dtos.UpdateProfessionalStudyRequest;
@RestController
@RequestMapping("/api/professional-studies")
public class ProfessionalStudyController {
    private final RegisterProfessionalStudyUseCase register;
    private final GetProfessionalStudyByIdUseCase get;
    private final ListProfessionalStudyUseCase list;
    private final UpdateProfessionalStudyUseCase update;
    private final DeleteProfessionalStudyUseCase delete;
    public ProfessionalStudyController(RegisterProfessionalStudyUseCase register, GetProfessionalStudyByIdUseCase get,
            ListProfessionalStudyUseCase list, UpdateProfessionalStudyUseCase update, DeleteProfessionalStudyUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<ProfessionalStudyResponse> create(@Valid @RequestBody CreateProfessionalStudyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterProfessionalStudyCommand(new StudyId(request.studyId()), new ProfessionalId(request.professionalId()), request.title(), request.university(), request.isValid(), request.resolutionNumber(), new CountryId(request.countryId()))));
    }
    @GetMapping
    public List<ProfessionalStudyResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public ProfessionalStudyResponse findById(@PathVariable("id") UUID id) { return get.execute(new ProfessionalStudyId(id)); }
    @PutMapping("/{id}")
    public ProfessionalStudyResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateProfessionalStudyRequest request) {
        return update.execute(new UpdateProfessionalStudyCommand(new ProfessionalStudyId(id), new StudyId(request.studyId()), new ProfessionalId(request.professionalId()), request.title(), request.university(), request.isValid(), request.resolutionNumber(), new CountryId(request.countryId())));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new ProfessionalStudyId(id));
        return ResponseEntity.noContent().build();
    }
}
