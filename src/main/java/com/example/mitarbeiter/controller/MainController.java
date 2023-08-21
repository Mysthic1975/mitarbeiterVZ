package com.example.mitarbeiter.controller;

import com.example.mitarbeiter.entity.EmployeeEntity;
import com.example.mitarbeiter.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Page;

@Controller
public class MainController {

    private final EmployeeService employeeService;

    public MainController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        Model model) {
        Page<EmployeeEntity> employees = employeeService.getAllEmployees(page, size);
        model.addAttribute("employees", employees.getContent());
        model.addAttribute("page", employees.getNumber());
        model.addAttribute("totalPages", employees.getTotalPages());
        return "employee/list";
    }

    @GetMapping("/sort/{sortBy}/{sortDirection}")
    public String sortEmployees(@PathVariable String sortBy,
                                @PathVariable String sortDirection,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                Model model) {
        Page<EmployeeEntity> employees = employeeService.getAllEmployeesSortedBy(sortBy, sortDirection, page, size);
        addSortAttributesToModel(model, employees, sortBy, sortDirection);
        return "employee/list";
    }

    private void addSortAttributesToModel(Model model, Page<EmployeeEntity> employees, String sortBy, String sortDirection) {
        model.addAttribute("employees", employees.getContent());
        model.addAttribute("page", employees.getNumber());
        model.addAttribute("totalPages", employees.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", sortDirection);
    }
}


