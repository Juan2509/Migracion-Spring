package com.migracion.rangel.infrastructure.country.adapters.in.rest.controllers;

import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.country.command.RegisterCountryCommand;
import com.migracion.rangel.application.country.command.UpdateCountryCommand;
import com.migracion.rangel.application.country.dto.CountryResponse;
import com.migracion.rangel.application.country.usecase.RegisterCountryUseCase;
import com.migracion.rangel.application.country.usecase.GetCountryByIdUseCase;
import com.migracion.rangel.application.country.usecase.ListCountryUseCase;
import com.migracion.rangel.application.country.usecase.UpdateCountryUseCase;
import com.migracion.rangel.application.country.usecase.DeleteCountryUseCase;
import com.migracion.rangel.domain.country.model.valueobject.CountryId;
import com.migracion.rangel.infrastructure.country.adapters.in.rest.dtos.CreateCountryRequest;
import com.migracion.rangel.infrastructure.country.adapters.in.rest.dtos.UpdateCountryRequest;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
    private final RegisterCountryUseCase register;
    private final GetCountryByIdUseCase get;
    private final ListCountryUseCase list;
    private final UpdateCountryUseCase update;
    private final DeleteCountryUseCase delete;

    public CountryController(RegisterCountryUseCase register, GetCountryByIdUseCase get,
            ListCountryUseCase list, UpdateCountryUseCase update, DeleteCountryUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }

    @PostMapping
    public ResponseEntity<CountryResponse> create(@Valid @RequestBody CreateCountryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                register.execute(new RegisterCountryCommand(request.nameCountry(), request.codeCountry(), request.description(), request.isActive(), request.telephonePrefix())));
    }

    @GetMapping
    public List<CountryResponse> findAll() { return list.execute(); }

    @GetMapping("/{id}")
    public CountryResponse findById(@PathVariable("id") UUID id) { return get.execute(new CountryId(id)); }

    @PutMapping("/{id}")
    public CountryResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateCountryRequest request) {
        return update.execute(new UpdateCountryCommand(new CountryId(id), request.nameCountry(), request.codeCountry(), request.description(), request.isActive(), request.telephonePrefix()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new CountryId(id));
        return ResponseEntity.noContent().build();
    }
}
