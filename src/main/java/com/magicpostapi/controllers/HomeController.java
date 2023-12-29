package com.magicpostapi.controllers;

import com.magicpostapi.components.AuthenticationResponse;
import com.magicpostapi.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class HomeController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ResponseEntity<AuthenticationResponse> login(@RequestBody Map<String, String> user) {
        return ResponseEntity.ok(authenticationService.authenticate(user));
    }

}
