package com.example.mitarbeiter.service;

import com.example.mitarbeiter.entity.UserEntity;
import java.util.List;

public interface UserService {
    List<UserEntity> getAllUser();
    UserEntity getUserById(Long userId);
    void saveUser(UserEntity user);
    void updateUser(UserEntity user, Long userId);
    void deleteUseryId(Long userId);
}
