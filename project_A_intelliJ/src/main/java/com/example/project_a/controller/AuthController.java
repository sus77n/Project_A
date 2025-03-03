package com.example.project_a.controller;

import com.example.project_a.model.User;
import com.example.project_a.repository.UserRepository;
import com.example.project_a.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.util.Optional;


@Controller
@RequestMapping
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "shop/forgot-password";
    }

    @PostMapping("/auth/forgot-password")
    public String forgotPassword(@RequestParam String email, RedirectAttributes redirectAttributes) throws MessagingException, UnsupportedEncodingException {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email not found! Please enter a valid email.");
            return "redirect:/forgot-password";
        }

        userService.sendPasswordResetToken(email);
        redirectAttributes.addFlashAttribute("successMessage", "Password reset link has been sent to your email.");

        return "redirect:/forgot-password";
    }


    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam(required = false) String token,
                                        @RequestParam(required = false) Boolean success,
                                        Model model) {
        if (token != null) {
            model.addAttribute("token", token);
        }
        model.addAttribute("success", success);
        return "shop/reset-password";
    }


    @PostMapping("/auth/reset-password")
    public String resetPassword(
            @RequestParam String token,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            RedirectAttributes redirectAttributes) {

        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Passwords do not match!");
            return "redirect:/reset-password?token=" + token;
        }

        Optional<User> userOpt = userRepository.findByResetToken(token);
        if (userOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid or expired token.");
            return "redirect:/reset-password?token=" + token;
        }

        User user = userOpt.get();
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setResetToken(null);
        userRepository.save(user);

        redirectAttributes.addFlashAttribute("successMessage", "Your password has been reset successfully. Please log in.");
        return "redirect:/reset-password?success=true";
    }


}



