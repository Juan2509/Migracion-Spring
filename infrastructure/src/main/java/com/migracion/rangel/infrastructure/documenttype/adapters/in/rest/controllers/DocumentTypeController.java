package com.migracion.rangel.infrastructure.documenttype.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.documenttype.command.RegisterDocumentTypeCommand;
import com.migracion.rangel.application.documenttype.command.UpdateDocumentTypeCommand;
import com.migracion.rangel.application.documenttype.dto.DocumentTypeResponse;
import com.migracion.rangel.application.documenttype.usecase.RegisterDocumentTypeUseCase;
import com.migracion.rangel.application.documenttype.usecase.GetDocumentTypeByIdUseCase;
import com.migracion.rangel.application.documenttype.usecase.ListDocumentTypeUseCase;
import com.migracion.rangel.application.documenttype.usecase.UpdateDocumentTypeUseCase;
import com.migracion.rangel.application.documenttype.usecase.DeleteDocumentTypeUseCase;
import com.migracion.rangel.domain.documenttype.model.valueobject.DocumentTypeId;
import com.migracion.rangel.infrastructure.documenttype.adapters.in.rest.dtos.CreateDocumentTypeRequest;
import com.migracion.rangel.infrastructure.documenttype.adapters.in.rest.dtos.UpdateDocumentTypeRequest;
@RestController
@RequestMapping("/api/document-types")
public class DocumentTypeController {
    private final RegisterDocumentTypeUseCase register;
    private final GetDocumentTypeByIdUseCase get;
    private final ListDocumentTypeUseCase list;
    private final UpdateDocumentTypeUseCase update;
    private final DeleteDocumentTypeUseCase delete;
    public DocumentTypeController(RegisterDocumentTypeUseCase register, GetDocumentTypeByIdUseCase get,
            ListDocumentTypeUseCase list, UpdateDocumentTypeUseCase update, DeleteDocumentTypeUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<DocumentTypeResponse> create(@Valid @RequestBody CreateDocumentTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterDocumentTypeCommand(request.code(), request.name(), request.active())));
    }
    @GetMapping
    public List<DocumentTypeResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public DocumentTypeResponse findById(@PathVariable("id") UUID id) { return get.execute(new DocumentTypeId(id)); }
    @PutMapping("/{id}")
    public DocumentTypeResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateDocumentTypeRequest request) {
        return update.execute(new UpdateDocumentTypeCommand(new DocumentTypeId(id), request.code(), request.name(), request.active()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new DocumentTypeId(id));
        return ResponseEntity.noContent().build();
    }
}
