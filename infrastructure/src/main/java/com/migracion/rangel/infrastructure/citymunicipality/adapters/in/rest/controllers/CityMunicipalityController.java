package com.migracion.rangel.infrastructure.citymunicipality.adapters.in.rest.controllers;

import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.migracion.rangel.application.citymunicipality.command.RegisterCityMunicipalityCommand;
import com.migracion.rangel.application.citymunicipality.command.UpdateCityMunicipalityCommand;
import com.migracion.rangel.application.citymunicipality.dto.CityMunicipalityResponse;
import com.migracion.rangel.application.citymunicipality.usecase.RegisterCityMunicipalityUseCase;
import com.migracion.rangel.application.citymunicipality.usecase.GetCityMunicipalityByIdUseCase;
import com.migracion.rangel.application.citymunicipality.usecase.ListCityMunicipalityUseCase;
import com.migracion.rangel.application.citymunicipality.usecase.UpdateCityMunicipalityUseCase;
import com.migracion.rangel.application.citymunicipality.usecase.DeleteCityMunicipalityUseCase;
import com.migracion.rangel.domain.citymunicipality.model.valueobject.CityMunicipalityId;
import com.migracion.rangel.domain.stateregion.model.valueobject.StateRegionId;
import com.migracion.rangel.infrastructure.citymunicipality.adapters.in.rest.dtos.CreateCityMunicipalityRequest;
import com.migracion.rangel.infrastructure.citymunicipality.adapters.in.rest.dtos.UpdateCityMunicipalityRequest;

@RestController
@RequestMapping("/api/city-municipalities")
public class CityMunicipalityController {
    private final RegisterCityMunicipalityUseCase register;
    private final GetCityMunicipalityByIdUseCase get;
    private final ListCityMunicipalityUseCase list;
    private final UpdateCityMunicipalityUseCase update;
    private final DeleteCityMunicipalityUseCase delete;

    public CityMunicipalityController(RegisterCityMunicipalityUseCase register, GetCityMunicipalityByIdUseCase get,
            ListCityMunicipalityUseCase list, UpdateCityMunicipalityUseCase update, DeleteCityMunicipalityUseCase delete) {
        this.register = register;
        this.get = get;
        this.list = list;
        this.update = update;
        this.delete = delete;
    }
    @PostMapping
    public ResponseEntity<CityMunicipalityResponse> create(@Valid @RequestBody CreateCityMunicipalityRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                register.execute(new RegisterCityMunicipalityCommand(request.nameCity(), request.codeCiti(), request.description(), request.isActive(), new StateRegionId(request.regionId()))));
    }
    @GetMapping
    public List<CityMunicipalityResponse> findAll() { return list.execute(); }
    @GetMapping("/{id}")
    public CityMunicipalityResponse findById(@PathVariable("id") UUID id) { return get.execute(new CityMunicipalityId(id)); }
    @PutMapping("/{id}")
    public CityMunicipalityResponse update(@PathVariable("id") UUID id, @Valid @RequestBody UpdateCityMunicipalityRequest request) {
        return update.execute(new UpdateCityMunicipalityCommand(new CityMunicipalityId(id), request.nameCity(), request.codeCiti(), request.description(), request.isActive(), new StateRegionId(request.regionId())));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        delete.execute(new CityMunicipalityId(id));
        return ResponseEntity.noContent().build();
    }
}
