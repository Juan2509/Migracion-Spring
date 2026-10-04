package com.migracion.rangel.infrastructure.sendertype.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.sendertype.command.RegisterSenderTypeCommand;
import com.migracion.rangel.application.sendertype.command.UpdateSenderTypeCommand;
import com.migracion.rangel.application.sendertype.dto.SenderTypeResponse;
import com.migracion.rangel.application.sendertype.usecase.RegisterSenderTypeUseCase;
import com.migracion.rangel.application.sendertype.usecase.GetSenderTypeByIdUseCase;
import com.migracion.rangel.application.sendertype.usecase.ListSenderTypeUseCase;
import com.migracion.rangel.application.sendertype.usecase.UpdateSenderTypeUseCase;
import com.migracion.rangel.application.sendertype.usecase.DeleteSenderTypeUseCase;
import com.migracion.rangel.domain.sendertype.model.valueobject.SenderTypeId;
import com.migracion.rangel.infrastructure.sendertype.adapters.in.rest.dtos.CreateSenderTypeRequest;
import com.migracion.rangel.infrastructure.sendertype.adapters.in.rest.dtos.UpdateSenderTypeRequest;
@RestController
@RequestMapping("/api/sender-types")
public class SenderTypeController {
    private final RegisterSenderTypeUseCase register;
    private final GetSenderTypeByIdUseCase get;
    private final ListSenderTypeUseCase list;
    private final UpdateSenderTypeUseCase update;
    private final DeleteSenderTypeUseCase delete;
    public SenderTypeController(RegisterSenderTypeUseCase register, GetSenderTypeByIdUseCase get,
            ListSenderTypeUseCase list, UpdateSenderTypeUseCase update, DeleteSenderTypeUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<SenderTypeResponse> create(@Valid @RequestBody CreateSenderTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterSenderTypeCommand(request.nameType())));
    }
    @GetMapping
    public List<SenderTypeResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public SenderTypeResponse findById(@PathVariable("id") UUID id) { return get.execute(new SenderTypeId(id)); }
    @PutMapping("/{id}")
    public SenderTypeResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateSenderTypeRequest request) {
        return update.execute(new UpdateSenderTypeCommand(new SenderTypeId(id), request.nameType()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new SenderTypeId(id));
        return ResponseEntity.noContent().build();
    }
}
