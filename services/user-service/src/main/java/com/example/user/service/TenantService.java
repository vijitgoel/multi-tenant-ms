package com.example.user.service;

import com.example.user.dto.TenantDTO;
import com.example.user.entity.Tenant;
import com.example.user.repository.TenantRepository;
import org.springframework.stereotype.Service;

@Service
public class TenantService {
    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public Tenant createTenant(TenantDTO dto) {
        Tenant tenant = new Tenant();
        tenant.setName(dto.getName());
        tenant.setAdminEmail(dto.getAdminEmail());
        return tenantRepository.save(tenant);
    }

    public Tenant getTenant(String id) {
        return tenantRepository.findById(id).orElse(null);
    }
}
