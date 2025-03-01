package com.example.project_a.service;

import com.example.project_a.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.project_a.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

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

//    @Transactional()
//    public boolean existsByUsername(String username) {
//        return userRepository.existsByUsername(username);
//    }

    public void sendPasswordResetToken(String email) throws MessagingException, UnsupportedEncodingException {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found with email: " + email);
        }

        User user = userOpt.get();
        String token = UUID.randomUUID().toString(); // Generate a unique token
        user.setresetToken(token);
        userRepository.save(user);

        sendResetEmail(user.getEmail(), token);
    }

    private void sendResetEmail(String to, String token) throws MessagingException, UnsupportedEncodingException {
        String resetLink = "http://localhost:8080/reset-password?token=" + token;

//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(email);
//        message.setSubject("Password Reset Request");
//        message.setText("Click the link below to reset your password:\n" + resetLink);

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
