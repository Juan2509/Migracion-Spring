package com.migracion.rangel.infrastructure.emailcontact.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.emailcontact.command.RegisterEmailContactCommand;
import com.migracion.rangel.application.emailcontact.command.UpdateEmailContactCommand;
import com.migracion.rangel.application.emailcontact.dto.EmailContactResponse;
import com.migracion.rangel.application.emailcontact.usecase.RegisterEmailContactUseCase;
import com.migracion.rangel.application.emailcontact.usecase.GetEmailContactByIdUseCase;
import com.migracion.rangel.application.emailcontact.usecase.ListEmailContactUseCase;
import com.migracion.rangel.application.emailcontact.usecase.UpdateEmailContactUseCase;
import com.migracion.rangel.application.emailcontact.usecase.DeleteEmailContactUseCase;
import com.migracion.rangel.domain.emailcontact.model.valueobject.EmailContactId;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.infrastructure.emailcontact.adapters.in.rest.dtos.CreateEmailContactRequest;
import com.migracion.rangel.infrastructure.emailcontact.adapters.in.rest.dtos.UpdateEmailContactRequest;
@RestController
@RequestMapping("/api/email-contacts")
public class EmailContactController {
    private final RegisterEmailContactUseCase register;
    private final GetEmailContactByIdUseCase get;
    private final ListEmailContactUseCase list;
    private final UpdateEmailContactUseCase update;
    private final DeleteEmailContactUseCase delete;
    public EmailContactController(RegisterEmailContactUseCase register, GetEmailContactByIdUseCase get,
            ListEmailContactUseCase list, UpdateEmailContactUseCase update, DeleteEmailContactUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<EmailContactResponse> create(@Valid @RequestBody CreateEmailContactRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(
                new RegisterEmailContactCommand(new ContactId(request.contactId()), request.email(), request.notes())));
    }
    @GetMapping
    public List<EmailContactResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public EmailContactResponse findById(@PathVariable("id") UUID id) { return get.execute(new EmailContactId(id)); }
    @PutMapping("/{id}")
    public EmailContactResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateEmailContactRequest request) {
        return update.execute(new UpdateEmailContactCommand(new EmailContactId(id),
                new ContactId(request.contactId()), request.email(), request.notes()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new EmailContactId(id));
        return ResponseEntity.noContent().build();
    }
}
