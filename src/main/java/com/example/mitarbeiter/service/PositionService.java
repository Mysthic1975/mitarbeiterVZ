package com.example.mitarbeiter.service;

import com.example.mitarbeiter.entity.PositionEntity;
import java.util.List;

public interface PositionService {
    List<PositionEntity> getAllPositions();
    PositionEntity getPositionById(Long id);
    void savePosition(PositionEntity position);
    void updatePosition(PositionEntity position, Long id);
    void deletePosition(Long id);
}

