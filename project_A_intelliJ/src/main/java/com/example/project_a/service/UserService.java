package com.example.project_a.service;

import com.example.project_a.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.project_a.repository.UserRepository;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public String hashingPassword(String plainPassword) {
        // Hash the password
        return passwordEncoder.encode(plainPassword);
    }

    public void save(User user) {
        String hashedPassword = hashingPassword(user.getPassword());
        user.setUsername(user.getEmail());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    public void update(User updatedUser) {
        User user = findUserById(updatedUser.getId());
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setDateOfBirth(updatedUser.getDateOfBirth());
        user.setEmail(updatedUser.getEmail());
        user.setAddress(updatedUser.getAddress());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        userRepository.save(user);
    }

    public User findUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public User findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
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

    public void sendPasswordResetToken(String email) throws MessagingException, UnsupportedEncodingException {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found with email: " + email);
        }

        User user = userOpt.get();
        String token = UUID.randomUUID().toString(); // Generate a unique token
        user.setResetToken(token);
        userRepository.save(user);

        sendResetEmail(user.getEmail(), token);
    }

    private void sendResetEmail(String to, String token) throws MessagingException, UnsupportedEncodingException {
        String resetLink = "http://localhost:8080/reset-password?token=" + token;

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("khongbiet4604@gmail.com", "Outstride Adventure"); // Change sender name
        helper.setTo(to);
        helper.setSubject("Reset Your Password");
        helper.setText("<p>Hello,</p>"
                        + "<p>You requested to reset your password. Click the link below:</p>"
                        + "<p><a href=\"" + resetLink + "\">Reset Password</a></p>"
                        + "<p>If you didn’t request this, please ignore this email.</p>",
                true); // true enables HTML
        mailSender.send(message);
    }

}
