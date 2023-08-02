package com.example.mitarbeiter.service.impl;

import com.example.mitarbeiter.entity.UserEntity;
import com.example.mitarbeiter.repository.UserRepository;
import com.example.mitarbeiter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public void saveUser(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(UserEntity user, Long userId) {
        UserEntity existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setPosition(user.getPosition());
            existingUser.setDepartment(user.getDepartment());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setProfilePicture(user.getProfilePicture());
            userRepository.save(existingUser);
        }
    }

    @Override
    public void deleteUseryId(Long userId) {
        userRepository.deleteById(userId);
    }
}
