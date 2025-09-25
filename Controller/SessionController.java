package com.charan.e_commerse_web.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    @GetMapping("/session-id")
    public String getSessionId(HttpSession session) {
        return "Session ID: " + session.getId();
    }
}
