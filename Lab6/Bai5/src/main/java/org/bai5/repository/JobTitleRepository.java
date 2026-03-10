package org.bai5.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.bai5.entity.JobTitle;

public interface JobTitleRepository extends JpaRepository<JobTitle, Long> {
    boolean existsByTitleIgnoreCase(String title);
}
