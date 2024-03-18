package com.example.moduleproject.controller;

import com.example.moduleproject.auth.AuthenticationResponse;
import com.example.moduleproject.auth.RegisterRequest;
import com.example.moduleproject.constant.Role;
import com.example.moduleproject.services.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService service;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/registerManager")
    public ResponseEntity<AuthenticationResponse> registerManager(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request, Role.MANAGER));
    }
}
