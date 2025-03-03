package com.example.project_a.controller;

import com.example.project_a.model.Order;
import com.example.project_a.model.OrderDetail;
import com.example.project_a.model.User;
import com.example.project_a.service.OrderService;
import com.example.project_a.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private OrderService orderService;

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

    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.deleteUserById(id);
            ra.addFlashAttribute("message", "The user has been deleted successfully.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "An error occurred while trying to delete the user.");
        }
        return "redirect:/admin/customer/list";
    }


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

    @PostMapping("/user/update/general")
    public String updateUserGeneral(@RequestParam String userId,
                                    @RequestParam String email,
                                    @RequestParam String phone,
                                    @RequestParam String DOB,
                                    RedirectAttributes ra, Model model) {
        User user = service.findUserById(Integer.parseInt(userId));
        user.setEmail(email);
        user.setPhoneNumber(phone);
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date dateOfBirth = formatter.parse(DOB);
            user.setDateOfBirth(dateOfBirth);
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Invalid Date Format!");
            return "redirect:/account";
        }
        service.update(user);
        User newUser = service.findUserById(Integer.parseInt(userId));

        // Update the authenticated user principal manually
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(newUser, null, user.getAuthorities())
        );
        ra.addFlashAttribute("message", "The user has been updated successfully.");
        return "redirect:/account";
    }

    @PostMapping("/user/update/address")
    public String updateUserAddress(@RequestParam String userId,
                                    @RequestParam String address,
                                    RedirectAttributes ra) {
        User user = service.findUserById(Integer.parseInt(userId));
        user.setAddress(address);
        service.update(user);

        User newUser = service.findUserById(Integer.parseInt(userId));

        // Update the authenticated user principal manually
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(newUser, null, user.getAuthorities())
        );
        ra.addFlashAttribute("message", "The user has been updated successfully.");
        return "redirect:/account";
    }

    @PostMapping("user/update/password")
    public String updateUserPassword(@RequestParam String userId,
                                     @RequestParam String currentPassword,
                                     @RequestParam String newPassword,
                                     @RequestParam String confirmPassword,
                                     RedirectAttributes ra) {
        User user = service.findUserById(Integer.parseInt(userId));
        if (service.checkPassword(currentPassword, user.getPassword())) {
            if (newPassword.equals(confirmPassword)) {
                user.setPassword(service.hashingPassword(newPassword));
                service.update(user);
            } else {
                ra.addFlashAttribute("error", "Confirm password must match with new password!");
                return "redirect:/account";
            }
        } else {
            ra.addFlashAttribute("error", "Wrong current password!");
            return "redirect:/account";
        }

        User newUser = service.findUserById(Integer.parseInt(userId));

        // Update the authenticated user principal manually
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(newUser, null, user.getAuthorities())
        );
        ra.addFlashAttribute("message", "The user has been updated successfully.");
        return "redirect:/account";
    }

    @GetMapping("/account")
    public String showAccount(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);

        // Assuming user.getId() returns the user's ID
        List<Order> orders = orderService.findOrdersByUserId(user.getId());
        model.addAttribute("orders", orders);

        return "shop/account";
    }
}
