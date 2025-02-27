package com.example.project_a.service;

import com.example.project_a.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.project_a.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public String hashingPassword(String plainPassword) {
        // Hash the password
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(plainPassword);
    }

    public void save(User user) {
        String hashedPassword = hashingPassword(user.getPassword());
        user.setPassword(hashedPassword);
        System.out.println("Saving user: " + user);
        userRepository.save(user);
    }

    public User findUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public void deleteUserById(Integer id) {
        if (findUserById(id) == null) {
            return;
        }
        userRepository.deleteById(id);
    }


    @Transactional()
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional()
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

}
