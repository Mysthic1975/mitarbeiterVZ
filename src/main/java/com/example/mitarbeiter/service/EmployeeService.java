package com.example.mitarbeiter.service;

import com.example.mitarbeiter.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface EmployeeService {
    Page<EmployeeEntity> getAllEmployees(int page, int size);

    Page<EmployeeEntity> getAllEmployeesSortedBy(String sortBy, String sortDirection, int page, int size);

    EmployeeEntity getEmployeeById(Long employeeId);

    Page<EmployeeEntity> searchEmployees(String search, Pageable pageable);

    void saveEmployee(EmployeeEntity employee);

    void updateEmployee(EmployeeEntity employee, Long employeeId, MultipartFile pictureFile);

    void deleteEmployee(Long employeeId);

    void deleteEmployeePicture(Long id);
}
