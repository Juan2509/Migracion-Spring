package com.migracion.rangel.infrastructure.contact.adapters.in.rest.controllers;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.contact.command.RegisterContactCommand;
import com.migracion.rangel.application.contact.command.UpdateContactCommand;
import com.migracion.rangel.application.contact.dto.ContactResponse;
import com.migracion.rangel.application.contact.usecase.RegisterContactUseCase;
import com.migracion.rangel.application.contact.usecase.GetContactByIdUseCase;
import com.migracion.rangel.application.contact.usecase.ListContactUseCase;
import com.migracion.rangel.application.contact.usecase.UpdateContactUseCase;
import com.migracion.rangel.application.contact.usecase.DeleteContactUseCase;
import com.migracion.rangel.domain.contact.model.valueobject.ContactId;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.professional.model.valueobject.ProfessionalId;
import com.migracion.rangel.infrastructure.contact.adapters.in.rest.dtos.CreateContactRequest;
import com.migracion.rangel.infrastructure.contact.adapters.in.rest.dtos.UpdateContactRequest;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {
    private final RegisterContactUseCase register;
    private final GetContactByIdUseCase get;
    private final ListContactUseCase list;
    private final UpdateContactUseCase update;
    private final DeleteContactUseCase delete;
    public ContactController(RegisterContactUseCase register, GetContactByIdUseCase get,
            ListContactUseCase list, UpdateContactUseCase update, DeleteContactUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<ContactResponse> create(@Valid @RequestBody CreateContactRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(register.execute(
                new RegisterContactCommand(request.fullName(), request.email(), request.notes(),
                        new CityMunicipalityId(request.cityId()), new ProfessionalId(request.createdBy()))));
    }
    @GetMapping
    public List<ContactResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public ContactResponse findById(@PathVariable("id") UUID id) { return get.execute(new ContactId(id)); }
    @PutMapping("/{id}")
    public ContactResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateContactRequest request) {
        return update.execute(new UpdateContactCommand(new ContactId(id), request.fullName(), request.email(),
                request.notes(), new CityMunicipalityId(request.cityId()),
                request.updatedBy() == null ? null : new ProfessionalId(request.updatedBy())));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new ContactId(id));
        return ResponseEntity.noContent().build();
    }
}
