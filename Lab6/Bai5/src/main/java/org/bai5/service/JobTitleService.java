package org.bai5.service;


import org.bai5.entity.JobTitle;
import org.bai5.repository.JobTitleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class JobTitleService {

    private final JobTitleRepository repo;

    public JobTitleService(JobTitleRepository repo) {
        this.repo = repo;
    }

    public void save(JobTitle jobTitle, MultipartFile file) throws Exception {

        if (repo.existsByTitleIgnoreCase(jobTitle.getTitle().trim())) {
            throw new Exception("Job Title đã tồn tại");
        }

        if (file != null && !file.isEmpty()) {
            if (file.getSize() > 1024 * 1024) {
                throw new Exception("File vượt quá 1MB");
            }
            jobTitle.setFileName(file.getOriginalFilename());
            jobTitle.setFileSize(file.getSize());
            jobTitle.setFileData(file.getBytes());
        }

        repo.save(jobTitle);
    }
}
