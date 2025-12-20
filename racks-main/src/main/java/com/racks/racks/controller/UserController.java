package com.racks.racks.controller;

import com.racks.racks.model.User;
import com.racks.racks.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Create a new user
    @PostMapping
    public User createUser(@RequestParam String name) {
        return userService.createUser(name);
    }

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    // Update user
    @PutMapping("/{id}")
    public User updateUser(@PathVariable long id, @RequestParam String name) {
        return userService.updateUser(id, name);
    }

    // Delete user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }
}
