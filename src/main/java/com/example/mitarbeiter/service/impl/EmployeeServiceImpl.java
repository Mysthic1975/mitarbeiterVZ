package com.example.mitarbeiter.service.impl;

import com.example.mitarbeiter.entity.ProfilePictureEntity;
import com.example.mitarbeiter.entity.EmployeeEntity;
import com.example.mitarbeiter.repository.EmployeeRepository;
import com.example.mitarbeiter.service.EmployeeService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
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
    public void updateEmployee(EmployeeEntity employee, Long employeeId, MultipartFile pictureFile) {
        EmployeeEntity existingEmployee = employeeRepository.findById(employeeId).orElse(null);
        if (existingEmployee != null) {
            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setLastName(employee.getLastName());
            existingEmployee.setPosition(employee.getPosition());
            existingEmployee.setDepartment(employee.getDepartment());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setPhoneNumber(employee.getPhoneNumber());

            updateProfilePicture(existingEmployee, pictureFile);

            employeeRepository.save(existingEmployee);
        }
    }

    @Override
    public void updateEmployeeWithoutPicture(EmployeeEntity employee, Long employeeId) {
        EmployeeEntity existingEmployee = employeeRepository.findById(employeeId).orElse(null);
        if (existingEmployee != null) {
            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setLastName(employee.getLastName());
            existingEmployee.setPosition(employee.getPosition());
            existingEmployee.setDepartment(employee.getDepartment());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setPhoneNumber(employee.getPhoneNumber());

            employeeRepository.save(existingEmployee);
        }
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public void deleteEmployeePicture(Long id) {
        EmployeeEntity employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        employee.setProfilePicture(null);
        employeeRepository.save(employee);
    }

    @Override
    public Page<EmployeeEntity> searchEmployees(String search, Pageable pageable) {
        return employeeRepository.searchEmployees(search, pageable);
    }

    private void updateProfilePicture(EmployeeEntity employee, MultipartFile pictureFile) {
        try {
            byte[] pictureData = pictureFile.getBytes();
            ProfilePictureEntity profilePicture = employee.getProfilePicture();
            if (profilePicture == null) {
                profilePicture = new ProfilePictureEntity();
                profilePicture.setEmployee(employee);
                employee.setProfilePicture(profilePicture);
            }

            // Erstelle ein neues ProfilePictureEntity
            profilePicture.setPictureData(BlobProxy.generateProxy(pictureData));
        } catch (Exception e) {
            // Handle die IOException, falls eine auftritt
            e.printStackTrace();
        }
    }
}














