package com.example.taskapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/login")
    public ResponseEntity<Void> login() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/success")
    public ResponseEntity<Map<String, Object>> success(@AuthenticationPrincipal OidcUser user) {
        Map<String, Object> response = Map.of(
            "username", user.getName(),
            "email", user.getEmail() != null ? user.getEmail() : ""
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me(@AuthenticationPrincipal OidcUser user) {
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Not authenticated"));
        }
        Map<String, Object> response = Map.of(
            "username", user.getName(),
            "email", user.getEmail() != null ? user.getEmail() : ""
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }
}
