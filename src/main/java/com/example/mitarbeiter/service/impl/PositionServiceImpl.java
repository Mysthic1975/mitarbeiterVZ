package com.example.mitarbeiter.service.impl;

import com.example.mitarbeiter.entity.PositionEntity;
import com.example.mitarbeiter.repository.PositionRepository;
import com.example.mitarbeiter.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    @Autowired
    public PositionServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public List<String> getPositionNamesByIds(List<Long> positionIds) {
        List<PositionEntity> positions = positionIds.stream()
                .map(this::getPositionById)
                .collect(Collectors.toList());

        return positions.stream()
                .map(PositionEntity::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<PositionEntity> getAllPositions() {
        return positionRepository.findAll();
    }

    @Override
    public PositionEntity getPositionById(Long id) {
        return positionRepository.findById(id).orElse(null);
    }

    @Override
    public void savePosition(PositionEntity position) {
        positionRepository.save(position);
    }

    @Override
    public void updatePosition(PositionEntity position, Long id) {
        PositionEntity existingPosition = positionRepository.findById(id).orElse(null);
        if (existingPosition != null) {
            existingPosition.setName(position.getName());
            positionRepository.save(existingPosition);
        }
    }

    @Override
    public void deletePosition(Long id) {
        positionRepository.deleteById(id);
    }
}


