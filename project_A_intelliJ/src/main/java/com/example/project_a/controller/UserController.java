package com.example.project_a.controller;

import com.example.project_a.model.User;
import com.example.project_a.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/user")
    public String user(Model model) {
        List<User> users = service.getAllUsers();
        model.addAttribute("users", users);
        return "user";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "shop/register";
    }

    @ResponseBody
    @PostMapping("/users/save")
    public ResponseEntity<String> saveUser(User user) {
        try {
            service.save(user);
            System.out.println("saved user: " + user);
            return ResponseEntity.ok("User registration successful");
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed");
        }
    }


    @GetMapping("/admin/customer/list")
    public String showCustomer(Model model) {
        List<User> users = service.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("pageTitle", "Customer List");
        return "admin/customer-list";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.deleteUserById(id);
            ra.addFlashAttribute("message", "The user has been deleted successfully.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "An error occurred while trying to delete the user.");
        }
        return "redirect:/admin/customer/list";
    }

    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/users/check/email")
    @ResponseBody
    public Map<String, Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = service.existsByEmail(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return response;
    }

//    @GetMapping("/users/check/username")
//    @ResponseBody
//    public Map<String, Boolean> checkUsernameExists(@RequestParam String username) {
//        boolean exists = service.existsByUsername(username);
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("exists", exists);
//        return response;
//    }
}
