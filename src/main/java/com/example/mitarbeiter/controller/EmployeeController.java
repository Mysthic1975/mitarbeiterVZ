package com.example.mitarbeiter.controller;

import com.example.mitarbeiter.entity.EmployeeEntity;
import com.example.mitarbeiter.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Arrays;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private static final List<String> POSITIONS = Arrays.asList("Tester", "Entwickler", "PO", "Scrum Master");

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String getAllEmployees(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  Model model) {
        if (page < 0) {
            page = 0;
        }
        List<EmployeeEntity> employees = employeeService.getAllEmployees(page, size);
        model.addAttribute("employees", employees);
        model.addAttribute("page", page);
        return "employee/list";
    }

    @GetMapping("/sort/{sortBy}/{sortDirection}")
    public String getAllEmployeesSortedBy(@PathVariable String sortBy, @PathVariable String sortDirection,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          Model model) {
        List<EmployeeEntity> employees = employeeService.getAllEmployeesSortedBy(sortBy, sortDirection, page, size);
        model.addAttribute("employees", employees);
        model.addAttribute("page", page);
        return "employee/list";
    }

    @GetMapping("/{id}")
    public String getEmployeeById(@PathVariable Long id, Model model) {
        EmployeeEntity employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "employee/view";
    }

    @PostMapping
    public String saveEmployee(@ModelAttribute EmployeeEntity employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    @PutMapping("/{id}")
    public String updateEmployee(@ModelAttribute EmployeeEntity employee, @PathVariable Long id) {
        employeeService.updateEmployee(employee, id);
        return "redirect:/";
    }

    @GetMapping("/create")
    public String showCreateEmployeeForm(Model model) {
        model.addAttribute("employee", new EmployeeEntity());
        model.addAttribute("positions", POSITIONS);
        return "employee/create";
    }

    @PostMapping("/create")
    public String createEmployee(@ModelAttribute EmployeeEntity employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String showEditEmployeeForm(@PathVariable Long id, Model model) {
        EmployeeEntity employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("positions", POSITIONS);
        return "employee/edit";
    }

    @PostMapping("/{id}/edit")
    public String editEmployee(@ModelAttribute EmployeeEntity employee, @PathVariable Long id) {
        employeeService.updateEmployee(employee, id);
        return "redirect:/employee/" + id;
    }

    @GetMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchEmployees(@RequestParam String search,
                                  @RequestParam(defaultValue = "0") int page,
                                  Model model) {
        List<EmployeeEntity> employees = employeeService.searchEmployees(search);
        model.addAttribute("employees", employees);
        model.addAttribute("page", page);
        return "employee/list";
    }

}