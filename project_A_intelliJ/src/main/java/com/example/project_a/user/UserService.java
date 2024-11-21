package com.example.project_a.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;
     private  BCryptPasswordEncoder passwordEncoder;

    public UserService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public String hashingPassword(String plainPassword) {
        // Hash the password
        return passwordEncoder.encode(plainPassword);
    }

    public void save(User user) {
        String hashedPassword = hashingPassword(user.getPassword());
        user.setPassword(hashedPassword);
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

}
