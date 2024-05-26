package com.example.careplane.controller;

import com.example.careplane.entity.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @GetMapping("/list")
    public Role[] getAllRoles() {

        return Role.values();
    }
}
