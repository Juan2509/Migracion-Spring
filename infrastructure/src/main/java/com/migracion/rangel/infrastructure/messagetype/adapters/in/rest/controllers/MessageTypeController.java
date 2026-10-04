package com.migracion.rangel.infrastructure.messagetype.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.messagetype.command.RegisterMessageTypeCommand;
import com.migracion.rangel.application.messagetype.command.UpdateMessageTypeCommand;
import com.migracion.rangel.application.messagetype.dto.MessageTypeResponse;
import com.migracion.rangel.application.messagetype.usecase.RegisterMessageTypeUseCase;
import com.migracion.rangel.application.messagetype.usecase.GetMessageTypeByIdUseCase;
import com.migracion.rangel.application.messagetype.usecase.ListMessageTypeUseCase;
import com.migracion.rangel.application.messagetype.usecase.UpdateMessageTypeUseCase;
import com.migracion.rangel.application.messagetype.usecase.DeleteMessageTypeUseCase;
import com.migracion.rangel.domain.messagetype.model.valueobject.MessageTypeId;
import com.migracion.rangel.infrastructure.messagetype.adapters.in.rest.dtos.CreateMessageTypeRequest;
import com.migracion.rangel.infrastructure.messagetype.adapters.in.rest.dtos.UpdateMessageTypeRequest;
@RestController
@RequestMapping("/api/message-types")
public class MessageTypeController {
    private final RegisterMessageTypeUseCase register;
    private final GetMessageTypeByIdUseCase get;
    private final ListMessageTypeUseCase list;
    private final UpdateMessageTypeUseCase update;
    private final DeleteMessageTypeUseCase delete;
    public MessageTypeController(RegisterMessageTypeUseCase register, GetMessageTypeByIdUseCase get,
            ListMessageTypeUseCase list, UpdateMessageTypeUseCase update, DeleteMessageTypeUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<MessageTypeResponse> create(@Valid @RequestBody CreateMessageTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(new RegisterMessageTypeCommand(request.nameType())));
    }
    @GetMapping
    public List<MessageTypeResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public MessageTypeResponse findById(@PathVariable("id") UUID id) { return get.execute(new MessageTypeId(id)); }
    @PutMapping("/{id}")
    public MessageTypeResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateMessageTypeRequest request) {
        return update.execute(new UpdateMessageTypeCommand(new MessageTypeId(id), request.nameType()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new MessageTypeId(id));
        return ResponseEntity.noContent().build();
    }
}
