package com.example.mitarbeiter.controller;

import com.example.mitarbeiter.entity.UserEntity;
import com.example.mitarbeiter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserEntity> getAllUser() {
        List<UserEntity> users = userService.getAllUser();
        System.out.println("users : " + users);
        return users;
    }

    @GetMapping("/{userID}")
    public UserEntity getUserById(@PathVariable Long userId) {
        UserEntity user = userService.getUserById(userId);
        System.out.println("userId : " + userId + " : user : " + user);
        return user;
    }

    @PostMapping
    public String saveUser(@RequestBody UserEntity user) {
        userService.saveUser(user);
        return "user saved successfully.";
    }

    @PutMapping("/{userId}")
    public String updateUser(@RequestBody UserEntity user, @PathVariable Long userId) {
        userService.updateUser(user, userId);
        return "user updated successfully.";
    }

    @DeleteMapping("{userId}")
    public String deleteUserId(@PathVariable Long userId) {
        userService.deleteUseryId(userId);
        return "user deleted successfully.";
    }
}

