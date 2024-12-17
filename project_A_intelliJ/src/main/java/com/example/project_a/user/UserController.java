package com.example.project_a.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;

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

//    @GetMapping("/register")
//    public String showRegister(Model model) {
//        model.addAttribute("user", new User());
//        return "shop/register";
//    }


    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes ra) {

        service.save(user);
        ra.addFlashAttribute("message", "The user has been saved successfully.");
        return "redirect:/register";
    }

    private BCryptPasswordEncoder passwordEncoder;

}
