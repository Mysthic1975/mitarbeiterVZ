package com.example.mitarbeiter.service.impl;

import com.example.mitarbeiter.entity.EmployeeEntity;
import com.example.mitarbeiter.entity.PositionEntity;
import com.example.mitarbeiter.repository.EmployeeRepository;
import com.example.mitarbeiter.service.EmployeeService;
import com.example.mitarbeiter.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PositionService positionService; // Hinzufügen

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PositionService positionService) {
        this.employeeRepository = employeeRepository;
        this.positionService = positionService; // Initialisieren
    }

    @Override
    public Page<EmployeeEntity> getAllEmployees(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Page<EmployeeEntity> getAllEmployeesSortedBy(String sortBy, String sortDirection, int page, int size) {
        Sort sort = Sort.by(sortDirection.equals("asc") ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy));
        Pageable pageable = PageRequest.of(page, size, sort);
        return employeeRepository.findAll(pageable);
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

    @Override
    public Page<EmployeeEntity> searchEmployees(String search, Pageable pageable) {
        return employeeRepository.searchEmployees(search, pageable);
    }

    public List<String> getPositionNamesByIds(List<Long> positionIds) {
        return positionIds.stream()
                .map(positionService::getPositionById)
                .map(PositionEntity::getName)
                .collect(Collectors.toList());
    }

}


