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

    private static final List<String> POSITIONS = Arrays.asList("Tester", "Entwickler", "PO", "Scrummaster");

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String getAllEmployees(Model model) {
        List<EmployeeEntity> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employee/list";
    }

    @GetMapping("/{id}")
    public String getEmployeeById(@PathVariable Long id, Model model) {
        EmployeeEntity employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "employee/view";
    }
    @GetMapping("/sort/{sortBy}/{sortDirection}")
    public String getAllEmployeesSortedBy(@PathVariable String sortBy, @PathVariable String sortDirection, Model model) {
        List<EmployeeEntity> employees = employeeService.getAllEmployeesSortedBy(sortBy, sortDirection);
        model.addAttribute("employees", employees);
        return "employee/list";
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

    // Endpunkt zum Anzeigen des Formulars zum Hinzufügen eines neuen Mitarbeiters
    @GetMapping("/create")
    public String showCreateEmployeeForm(Model model) {
        model.addAttribute("employee", new EmployeeEntity());
        model.addAttribute("positions", POSITIONS);
        return "employee/create";
    }

    // Endpunkt zum Speichern eines neuen Mitarbeiters
    @PostMapping("/create")
    public String createEmployee(@ModelAttribute EmployeeEntity employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    // Endpunkt zum Anzeigen des Formulars zum Bearbeiten eines vorhandenen Mitarbeiters
    @GetMapping("/{id}/edit")
    public String showEditEmployeeForm(@PathVariable Long id, Model model) {
        EmployeeEntity employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("positions", POSITIONS);
        return "employee/edit";
    }

    // Endpunkt zum Speichern der Änderungen an einem vorhandenen Mitarbeiter
    @PostMapping("/{id}/edit")
    public String editEmployee(@ModelAttribute EmployeeEntity employee, @PathVariable Long id) {
        employeeService.updateEmployee(employee, id);
        return "redirect:/employee/" + id;
    }

    // Endpunkt zum Löschen eines vorhandenen Mitarbeiters
    @GetMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchEmployees(@RequestParam String search, Model model) {
        List<EmployeeEntity> employees = employeeService.searchEmployees(search);
        model.addAttribute("employees", employees);
        return "employee/list";
    }

}
