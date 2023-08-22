package com.example.mitarbeiter;

import com.example.mitarbeiter.entity.PositionEntity;
import com.example.mitarbeiter.repository.PositionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final PositionRepository positionRepository;

    public DataLoader(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        addPositionIfNotExists("Tester");
        addPositionIfNotExists("Entwickler");
        addPositionIfNotExists("PO");
        addPositionIfNotExists("Scrum Master");
    }

    private void addPositionIfNotExists(String positionName) {
        if (positionRepository.findByName(positionName) == null) {
            PositionEntity position = new PositionEntity();
            position.setName(positionName);
            positionRepository.save(position);
        }
    }
}

