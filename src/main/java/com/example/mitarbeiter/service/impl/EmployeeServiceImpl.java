package com.example.mitarbeiter.service.impl;

import com.example.mitarbeiter.entity.EmployeeEntity;
import com.example.mitarbeiter.repository.EmployeeRepository;
import com.example.mitarbeiter.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EmployeeEntity> getAllEmployees(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmployeeEntity> employeePage = employeeRepository.findAll(pageable);
        return employeePage.getContent();
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
    public List<EmployeeEntity> searchEmployees(String search) {
        return employeeRepository.searchEmployees(search);
    }

    @Override
    public List<EmployeeEntity> getAllEmployeesSortedBy(String sortBy, String sortDirection) {
        if (sortDirection.equals("asc")) {
            return switch (sortBy) {
                case "id" -> employeeRepository.findAllByOrderByIdAsc();
                case "firstName" -> employeeRepository.findAllByOrderByFirstNameAsc();
                case "lastName" -> employeeRepository.findAllByOrderByLastNameAsc();
                case "position" -> employeeRepository.findAllByOrderByPositionAsc();
                case "department" -> employeeRepository.findAllByOrderByDepartmentAsc();
                case "email" -> employeeRepository.findAllByOrderByEmailAsc();
                default -> employeeRepository.findAll();
            };
        } else {
            return switch (sortBy) {
                case "id" -> employeeRepository.findAllByOrderByIdDesc();
                case "firstName" -> employeeRepository.findAllByOrderByFirstNameDesc();
                case "lastName" -> employeeRepository.findAllByOrderByLastNameDesc();
                case "position" -> employeeRepository.findAllByOrderByPositionDesc();
                case "department" -> employeeRepository.findAllByOrderByDepartmentDesc();
                case "email" -> employeeRepository.findAllByOrderByEmailDesc();
                default -> employeeRepository.findAll();
            };
        }
    }
}
