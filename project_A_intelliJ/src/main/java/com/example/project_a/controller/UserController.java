package com.example.project_a.controller;

import com.example.project_a.model.Order;
import com.example.project_a.model.User;
import com.example.project_a.service.OrderService;
import com.example.project_a.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "shop/register";
    }

    @PostMapping("/users/save")
    public String saveUser(User user,
                           @RequestParam String email,
                           HttpServletRequest request,
                           RedirectAttributes redirectAttributes) {
        System.out.println("email here: " + email);

        if (service.existsByEmail(email)) {
            System.out.println(service.existsByEmail(email));
            redirectAttributes.addFlashAttribute("error", "This email already exists");
            return "redirect:/register";
        }
        service.save(user);

        autoLogin(user, request);

        redirectAttributes.addFlashAttribute("message", "User registered successfully");
        return "redirect:/account";
    }


    @GetMapping("/admin/user/list")
    public String showCustomer(Model model) {
        List<User> users = service.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("pageTitle", "User List");
        return "admin/user-list";
    }

    @GetMapping("/admin/user/detail/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = service.findUserById(id);
        List<Order> orders = orderService.findOrdersByUserId(user.getId());
        model.addAttribute("pageTitle", "User List");
        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        return "admin/user-detail";
    }

    @GetMapping("/users/check/email")
    @ResponseBody
    public Map<String, Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = service.existsByEmail(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return response;
    }

    @PostMapping("/user/update/general")
    public String updateUserGeneral(@RequestParam String userId,
                                    @RequestParam String firstName,
                                    @RequestParam String lastName,
                                    @RequestParam String phone,
                                    @RequestParam String DOB,
                                    RedirectAttributes ra, Model model) {
        User user = service.findUserById(Integer.parseInt(userId));
        user.setFirstName(firstName);
        user.setLastName(lastName);
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
        return "redirect:/account#address";
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
            return "redirect:/account#password";
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

        List<Order> orders = orderService.findOrdersByUserId(user.getId());
        model.addAttribute("orders", orders);

        return "shop/account";
    }

    @GetMapping("/account/orders/details/{orderId}")
    public String showOrderDetails(@AuthenticationPrincipal User user,@PathVariable("orderId") String orderId, Model model) {
        Order order = orderService.findOrderById(Integer.parseInt(orderId));
        int totalItem = orderService.getAllOrders().size();
        model.addAttribute("totalItem", totalItem);
        model.addAttribute("order", order);
        model.addAttribute("user", user);
        return "shop/order-history";
    }

    @PostMapping("/admin/user/status/change")
    public String changeStatus(@RequestParam String userId, RedirectAttributes ra) {
        User user = service.findUserById(Integer.parseInt(userId));
        user.setStatus(user.getStatus().equals("Active") ? "Inactive" : "Active");
        service.save(user);
        ra.addFlashAttribute("message", "The User has been changed successfully.");
        return "redirect:/admin/user/list";
    }


    private void autoLogin(User user, HttpServletRequest request) {
        // Load user details from UserService
        UserDetails userDetails = service.findUserByEmail(user.getEmail());

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
        HttpSession session = request.getSession();
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
    }

}
