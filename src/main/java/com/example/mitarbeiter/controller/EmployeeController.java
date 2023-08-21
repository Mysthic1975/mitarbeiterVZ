package com.example.mitarbeiter.controller;

import com.example.mitarbeiter.entity.EmployeeEntity;
import com.example.mitarbeiter.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

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
                                  @RequestParam(defaultValue = "id") String sortBy,
                                  @RequestParam(defaultValue = "asc") String sortDirection,
                                  @RequestParam(defaultValue = "") String search,
                                  Model model) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortBy);
        Page<EmployeeEntity> employees = employeeService.searchEmployees(search, pageRequest);
//        Page<EmployeeEntity> employees = employeeService.getAllEmployeesSortedBy(sortBy, sortDirection, page, size);

        model.addAttribute("employees", employees.getContent());

        model.addAttribute("page", employees.getNumber());
        model.addAttribute("totalPages", employees.getTotalPages());

        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("search", search);
        model.addAttribute("sortField", sortBy);

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
                                  Model model) {
        Pageable pageable = Pageable.ofSize(10);

        Page<EmployeeEntity> employees = employeeService.searchEmployees(search, pageable);
        model.addAttribute("employees", employees.getContent());
        model.addAttribute("page", employees.getNumber());
        return "employee/list";
    }

}