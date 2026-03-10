package org.bai4.controller;


import org.bai4.entity.OrganizationUnit;
import org.bai4.service.OrganizationUnitService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/org-unit")
public class OrganizationUnitController {

    private final OrganizationUnitService service;

    public OrganizationUnitController(OrganizationUnitService service) {
        this.service = service;
    }

    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("orgUnit", new OrganizationUnit());
        return "add-org-unit";
    }

    @PostMapping("/add")
    public String submitForm(
            @Valid @ModelAttribute("orgUnit") OrganizationUnit orgUnit,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "add-org-unit";
        }

        try {
            service.create(orgUnit);
            model.addAttribute("success", "Tạo Organization Unit thành công!");
            model.addAttribute("orgUnit", new OrganizationUnit());
            return "add-org-unit";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "add-org-unit";
        }
    }

    @GetMapping("/cancel")
    public String cancel() {
        return "redirect:/org-unit/list";
    }


    @GetMapping("/list")
    public String list(
            @RequestParam(value = "q", required = false) String q,
            Model model
    ) {
        var all = service.findAll(q);
        model.addAttribute("items", all);
        model.addAttribute("q", q == null ? "" : q);
        return "list-org-unit";
    }

}
