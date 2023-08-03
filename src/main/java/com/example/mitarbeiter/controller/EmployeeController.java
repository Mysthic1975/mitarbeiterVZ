package com.example.mitarbeiter.controller;

import com.example.mitarbeiter.entity.EmployeeEntity;
import com.example.mitarbeiter.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeEntity> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{userId}")
    public EmployeeEntity getEmployeeById(@PathVariable Long userId) {
        return employeeService.getEmployeeById(userId);
    }

    @PostMapping
    public String saveEmployee(@RequestBody EmployeeEntity employee) {
        employeeService.saveEmployee(employee);
        return "employee saved successfully.";
    }

    @PutMapping("/{userId}")
    public String updateEmployee(@RequestBody EmployeeEntity employee, @PathVariable Long userId) {
        employeeService.updateEmployee(employee, userId);
        return "employee updated successfully.";
    }

    @DeleteMapping("{userId}")
    public String deleteEmployee(@PathVariable Long userId) {
        employeeService.deleteEmployee(userId);
        return "employee deleted successfully.";
    }
}



