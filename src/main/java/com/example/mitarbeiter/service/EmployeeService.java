package com.example.mitarbeiter.service;

import com.example.mitarbeiter.entity.EmployeeEntity;
import java.util.List;

public interface EmployeeService {
    List<EmployeeEntity> getAllEmployees();
    EmployeeEntity getEmployeeById(Long employeeId);
    void saveEmployee(EmployeeEntity employee);
    void updateEmployee(EmployeeEntity employee, Long employeeId);
    void deleteEmployee(Long employeeId);
}