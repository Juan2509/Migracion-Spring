package com.migracion.rangel.infrastructure.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityIntegrationTest {
    @Autowired MockMvc mvc;

    @Test
    void clientCanObtainTokenAndCreateCountry() throws Exception {
        var result = mvc.perform(get("/api/security/csrf")
                .with(httpBasic("test-user", "test-password")))
                .andExpect(status().isOk()).andReturn();
        var token = (CsrfToken) result.getRequest().getAttribute(CsrfToken.class.getName());
        var session = (MockHttpSession) result.getRequest().getSession(false);
        mvc.perform(post("/api/countries").with(httpBasic("test-user", "test-password"))
                .session(session).header(token.getHeaderName(), token.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"nameCountry":"Colombia","codeCountry":"CO",
                         "description":"Security integration test","isActive":true,"telephonePrefix":"+57"}
                        """))
                .andExpect(status().isCreated());
    }

    @Test
    void anonymousReadIsRejected() throws Exception {
        mvc.perform(get("/api/countries")).andExpect(status().isUnauthorized());
    }

    @Test
    void incorrectPasswordIsRejected() throws Exception {
        mvc.perform(get("/api/countries").with(httpBasic("test-user", "incorrect")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void authenticatedReadReachesDatabase() throws Exception {
        mvc.perform(get("/api/countries").with(httpBasic("test-user", "test-password")))
                .andExpect(status().isOk());
    }

    @Test
    void writeWithoutCsrfIsRejected() throws Exception {
        mvc.perform(post("/api/countries").with(httpBasic("test-user", "test-password"))
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void authenticatedWriteStillValidatesRequest() throws Exception {
        mvc.perform(post("/api/countries").with(httpBasic("test-user", "test-password"))
                .with(csrf()).contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest());
    }
}
