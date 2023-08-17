package com.example.mitarbeiter.service;

import com.example.mitarbeiter.entity.EmployeeEntity;
import java.util.List;

public interface EmployeeService {
    List<EmployeeEntity> getAllEmployees(int page, int size);
    List<EmployeeEntity> getAllEmployeesSortedBy(String sortBy, String sortDirection, int page, int size);
    EmployeeEntity getEmployeeById(Long employeeId);
    void saveEmployee(EmployeeEntity employee);
    void updateEmployee(EmployeeEntity employee, Long employeeId);
    void deleteEmployee(Long employeeId);
    List<EmployeeEntity> searchEmployees(String search);
}

