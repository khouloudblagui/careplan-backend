package com.example.careplane.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/medecin")
public class DoctorController {
    @GetMapping
    public String get() {
        return "GET:: medecin controller";
    }
    @PostMapping
    public String post() {
        return "POST:: medecin controller";
    }
    @PutMapping
    public String put() {
        return "PUT:: medecin controller";
    }
    @DeleteMapping
    public String delete() {
        return "DELETE:: medecin controller";
    }
}
