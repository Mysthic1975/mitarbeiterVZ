package com.example.mitarbeiter.controller;

import com.example.mitarbeiter.entity.EmployeeEntity;
import com.example.mitarbeiter.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    private final EmployeeService employeeService;

    public MainController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String index(Model model) {
        int defaultPage = 0;
        int defaultSize = 10;
        List<EmployeeEntity> employees = employeeService.getAllEmployees(defaultPage, defaultSize);
        model.addAttribute("employees", employees);
        model.addAttribute("page", defaultPage);
        return "employee/list";
    }

}
