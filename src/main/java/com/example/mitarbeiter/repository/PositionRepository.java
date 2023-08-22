package com.example.mitarbeiter.repository;

import com.example.mitarbeiter.entity.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<PositionEntity, Long> {
    PositionEntity findByName(String name);
}
