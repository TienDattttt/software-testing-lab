package org.bai4.repo;


import org.bai4.entity.OrganizationUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrganizationUnitRepository extends JpaRepository<OrganizationUnit, Long> {
    boolean existsByNameIgnoreCase(String name);
    boolean existsByUnitId(String unitId);

    @Query("""
    select o from OrganizationUnit o
    where lower(o.name) like %:q%
       or lower(coalesce(o.unitId,'')) like %:q%
""")
    List<OrganizationUnit> search(@Param("q") String q);
}
