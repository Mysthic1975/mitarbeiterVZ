package com.example.mitarbeiter.repository;

import com.example.mitarbeiter.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    // Methode zum Suchen von Mitarbeitern
    @Query("SELECT e FROM EmployeeEntity e WHERE lower(e.firstName) LIKE lower(concat('%', :search, '%')) OR lower(e.lastName) LIKE lower(concat('%', :search, '%')) OR lower(e.position) LIKE lower(concat('%', :search, '%')) OR lower(e.department) LIKE lower(concat('%', :search, '%'))")
    List<EmployeeEntity> searchEmployees(@Param("search") String search);
    Page<EmployeeEntity> findAllByOrderByIdAsc(Pageable pageable);
    Page<EmployeeEntity> findAllByOrderByFirstNameAsc(Pageable pageable);
    Page<EmployeeEntity> findAllByOrderByLastNameAsc(Pageable pageable);
    Page<EmployeeEntity> findAllByOrderByPositionAsc(Pageable pageable);
    Page<EmployeeEntity> findAllByOrderByDepartmentAsc(Pageable pageable);
    Page<EmployeeEntity> findAllByOrderByEmailAsc(Pageable pageable);

    Page<EmployeeEntity> findAllByOrderByIdDesc(Pageable pageable);
    Page<EmployeeEntity> findAllByOrderByFirstNameDesc(Pageable pageable);
    Page<EmployeeEntity> findAllByOrderByLastNameDesc(Pageable pageable);
    Page<EmployeeEntity> findAllByOrderByPositionDesc(Pageable pageable);
    Page<EmployeeEntity> findAllByOrderByDepartmentDesc(Pageable pageable);
    Page<EmployeeEntity> findAllByOrderByEmailDesc(Pageable pageable);
}