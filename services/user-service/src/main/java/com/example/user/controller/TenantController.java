package com.example.user.controller;

import com.example.user.dto.TenantDTO;
import com.example.user.entity.Tenant;
import com.example.user.service.TenantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping
    public ResponseEntity<Tenant> createTenant(@RequestBody TenantDTO dto) {
        return ResponseEntity.ok(tenantService.createTenant(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tenant> getTenant(@PathVariable String id) {
        return ResponseEntity.ok(tenantService.getTenant(id));
    }
}
