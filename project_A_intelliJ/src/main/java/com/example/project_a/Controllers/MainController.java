package com.example.project_a.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("")
    public String showHomePage() {return "index-2";}

    @GetMapping("/index-2")
    public String showIndexPage() {return "index-2";}

    @GetMapping("/shop")
    public String showProductPage() {return "shop";}

    @GetMapping("/blog")
    public String showBlogPage() {return "blog";}

    @GetMapping("/contact")
    public String showContactPage() {return "contact";}

    @GetMapping("/about")
    public String showAboutUsPage() {return "about";}
}
