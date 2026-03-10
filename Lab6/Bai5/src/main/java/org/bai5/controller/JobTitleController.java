package org.bai5.controller;


import org.bai5.entity.JobTitle;
import org.bai5.service.JobTitleService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/job-title")
public class JobTitleController {

    private final JobTitleService service;

    public JobTitleController(JobTitleService service) {
        this.service = service;
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("jobTitle", new JobTitle());
        return "add-job-title";
    }

    @PostMapping("/save")
    public String save(
            @Valid @ModelAttribute("jobTitle") JobTitle jobTitle,
            BindingResult result,
            @RequestParam("file") MultipartFile file,
            Model model) {

        if (result.hasErrors()) {
            return "add-job-title";
        }

        try {
            service.save(jobTitle, file);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "add-job-title";
        }

        return "redirect:/job-title/add";
    }
}
