package com.migracion.rangel.infrastructure.security.adapters.in.rest;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
    @GetMapping("/api/security/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }
}
