package com.example.mitarbeiter.repository;

import com.example.mitarbeiter.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    // Methode zum Suchen von Mitarbeitern
    @Query("SELECT e FROM EmployeeEntity e WHERE e.firstName LIKE %:search% OR e.lastName LIKE %:search% OR e.position LIKE %:search% OR e.department LIKE %:search%")
    List<EmployeeEntity> searchEmployees(@Param("search") String search);
}

