package com.cropadvisory.crop_advisory_backend.service;

import com.cropadvisory.crop_advisory_backend.entity.User;
import com.cropadvisory.crop_advisory_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {

    if (user.getEmail() == null ||
            user.getEmail().trim().isEmpty()) {

        throw new IllegalArgumentException(
                "Email cannot be empty");
    }

    return userRepository.save(user);
}

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(int id, User user) {

    if (user.getEmail() == null ||
            user.getEmail().trim().isEmpty()) {

        throw new IllegalArgumentException(
                "Email cannot be empty");
    }

    User existingUser =
            userRepository.findById(id).orElse(null);

    if (existingUser == null) {
        return null;
    }

    existingUser.setName(user.getName());
    existingUser.setEmail(user.getEmail());
    existingUser.setRole(user.getRole());
    existingUser.setPhone(user.getPhone());

    return userRepository.save(existingUser);
}
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}