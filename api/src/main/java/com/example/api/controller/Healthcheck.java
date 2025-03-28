package com.example.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class Healthcheck {

    @GetMapping("/hck")
    public ResponseEntity<String> hck() {
        return ResponseEntity.ok("Servidor Operando");
    }
}
