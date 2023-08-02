package com.example.mitarbeiter.service;

import com.example.mitarbeiter.entity.UserEntity;
import java.util.List;

public interface UserService {
    public List<UserEntity> getAllUser();
    public UserEntity getUserById(Long userId);
    public void saveUser(UserEntity user);
    public void updateUser(UserEntity user, Long userId);
    public void deleteUseryId(Long userId);
}
