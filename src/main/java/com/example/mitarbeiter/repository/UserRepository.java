package com.example.mitarbeiter.repository;

import com.example.mitarbeiter.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
