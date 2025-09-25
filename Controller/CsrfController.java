package com.charan.e_commerse_web.Controller;


import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController

public class CsrfController {
    @GetMapping("/csrf-token")
    @CrossOrigin(origins = "http://localhost:3000")
    public Map<String, String> getCsrfToken(HttpServletRequest request) {
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        if (token != null) {
            return Map.of(
                    "token", token.getToken(),
                    "headerName", token.getHeaderName(),
                    "parameterName", token.getParameterName()
            );
        } else {
            return Map.of("token", "", "headerName", "", "parameterName", "");
        }
    }
}
