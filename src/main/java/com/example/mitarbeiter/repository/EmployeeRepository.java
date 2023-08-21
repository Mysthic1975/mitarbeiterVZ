package com.example.mitarbeiter.repository;

import com.example.mitarbeiter.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    @Query("SELECT e FROM EmployeeEntity e WHERE lower(e.firstName) LIKE lower(concat('%', :search, '%')) OR lower(e.lastName) LIKE lower(concat('%', :search, '%')) OR lower(e.position) LIKE lower(concat('%', :search, '%')) OR lower(e.department) LIKE lower(concat('%', :search, '%'))")
    Page<EmployeeEntity> searchEmployees(@Param("search") String search, Pageable pageable);

}
