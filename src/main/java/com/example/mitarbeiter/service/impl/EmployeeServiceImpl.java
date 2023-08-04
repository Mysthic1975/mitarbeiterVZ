package com.example.mitarbeiter.service.impl;

import com.example.mitarbeiter.entity.EmployeeEntity;
import com.example.mitarbeiter.repository.EmployeeRepository;
import com.example.mitarbeiter.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EmployeeEntity> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public EmployeeEntity getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    @Override
    public void saveEmployee(EmployeeEntity employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void updateEmployee(EmployeeEntity employee, Long employeeId) {
        EmployeeEntity existingEmployee = employeeRepository.findById(employeeId).orElse(null);
        if (existingEmployee != null) {
            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setLastName(employee.getLastName());
            existingEmployee.setPosition(employee.getPosition());
            existingEmployee.setDepartment(employee.getDepartment());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setPhoneNumber(employee.getPhoneNumber());
            existingEmployee.setProfilePicture(employee.getProfilePicture());
            employeeRepository.save(existingEmployee);
        }
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    // Implementierung der Methode zum Suchen von Mitarbeitern
    @Override
    public List<EmployeeEntity> searchEmployees(String search) {
        return employeeRepository.searchEmployees(search);
    }
}

