package org.bai4.service;


import org.bai4.entity.OrganizationUnit;
import org.bai4.repo.OrganizationUnitRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class OrganizationUnitService {

    private final OrganizationUnitRepository repo;

    public OrganizationUnitService(OrganizationUnitRepository repo) {
        this.repo = repo;
    }

    public void create(OrganizationUnit ou) {
        if (ou.getName() != null) ou.setName(ou.getName().trim());
        if (ou.getUnitId() != null) ou.setUnitId(ou.getUnitId().trim());
        if (ou.getDescription() != null) ou.setDescription(ou.getDescription().trim());

        if (repo.existsByNameIgnoreCase(ou.getName())) {
            throw new IllegalArgumentException("Name đã tồn tại");
        }

        if (StringUtils.hasText(ou.getUnitId()) && repo.existsByUnitId(ou.getUnitId())) {
            throw new IllegalArgumentException("Unit Id đã tồn tại");
        }

        repo.save(ou);
    }

    public List<OrganizationUnit> findAll(String q) {
        if (q == null || q.trim().isEmpty()) {
            return repo.findAll();
        }
        String keyword = q.trim();
        return repo.search(keyword.toLowerCase());
    }
}
