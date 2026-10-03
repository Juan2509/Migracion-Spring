package com.migracion.rangel.infrastructure.phonecontact.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.phonecontact.command.RegisterPhoneContactCommand;
import com.migracion.rangel.application.phonecontact.command.UpdatePhoneContactCommand;
import com.migracion.rangel.application.phonecontact.dto.PhoneContactResponse;
import com.migracion.rangel.application.phonecontact.usecase.RegisterPhoneContactUseCase;
import com.migracion.rangel.application.phonecontact.usecase.GetPhoneContactByIdUseCase;
import com.migracion.rangel.application.phonecontact.usecase.ListPhoneContactUseCase;
import com.migracion.rangel.application.phonecontact.usecase.UpdatePhoneContactUseCase;
import com.migracion.rangel.application.phonecontact.usecase.DeletePhoneContactUseCase;
import com.migracion.rangel.domain.phonecontact.model.valueobject.PhoneContactId;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.infrastructure.phonecontact.adapters.in.rest.dtos.CreatePhoneContactRequest;
import com.migracion.rangel.infrastructure.phonecontact.adapters.in.rest.dtos.UpdatePhoneContactRequest;
@RestController
@RequestMapping("/api/phone-contacts")
public class PhoneContactController {
    private final RegisterPhoneContactUseCase register;
    private final GetPhoneContactByIdUseCase get;
    private final ListPhoneContactUseCase list;
    private final UpdatePhoneContactUseCase update;
    private final DeletePhoneContactUseCase delete;
    public PhoneContactController(RegisterPhoneContactUseCase register, GetPhoneContactByIdUseCase get,
            ListPhoneContactUseCase list, UpdatePhoneContactUseCase update, DeletePhoneContactUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<PhoneContactResponse> create(@Valid @RequestBody CreatePhoneContactRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(
                new RegisterPhoneContactCommand(new ContactId(request.contactId()), request.phone(), request.notes())));
    }
    @GetMapping
    public List<PhoneContactResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public PhoneContactResponse findById(@PathVariable("id") UUID id) { return get.execute(new PhoneContactId(id)); }
    @PutMapping("/{id}")
    public PhoneContactResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdatePhoneContactRequest request) {
        return update.execute(new UpdatePhoneContactCommand(new PhoneContactId(id),
                new ContactId(request.contactId()), request.phone(), request.notes()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new PhoneContactId(id));
        return ResponseEntity.noContent().build();
    }
}
