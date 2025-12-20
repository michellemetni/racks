package com.racks.racks.service;

import com.racks.racks.model.User;
import com.racks.racks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Create a new user
    public User createUser(String name) {
        User user = new User(name);
        return userRepository.save(user);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Update user
    public User updateUser(long id, String name) {
        User user = getUserById(id);
        user.setName(name);
        return userRepository.save(user);
    }

    // Delete user
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
