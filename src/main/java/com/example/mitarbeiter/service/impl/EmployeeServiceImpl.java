package com.example.mitarbeiter.service.impl;

import com.example.mitarbeiter.entity.ProfilePictureEntity;
import com.example.mitarbeiter.entity.EmployeeEntity;
import com.example.mitarbeiter.repository.EmployeeRepository;
import com.example.mitarbeiter.service.EmployeeService;
import com.example.mitarbeiter.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PositionService positionService) {
        this.employeeRepository = employeeRepository;
        // Hinzufügen
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

            // Update profile picture if provided
            if (employee.getProfilePictures() != null && !employee.getProfilePictures().isEmpty()) {
                ProfilePictureEntity profilePicture = employee.getProfilePictures().get(0);
                profilePicture.setEmployee(existingEmployee);

                // Set the profile pictures list to contain only the updated profile picture
                existingEmployee.setProfilePictures(Collections.singletonList(profilePicture));
            }

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

}


