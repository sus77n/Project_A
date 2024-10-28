package com.example.project_a.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("")
    public String showHomePage() {return "index-2.html";}

    @GetMapping("/index-2.html")
    public String showIndexPage() {return "index-2.html";}

    @GetMapping("/shop-list.html")
    public String showProductPage() {return "shop-list.html";}

    @GetMapping("/shop.html")
    public String showProductsPage() {return "shop.html";}

    @GetMapping("/blog.html")
    public String showBlogPage() {return "blog.html";}

    @GetMapping("/contact.html")
    public String showContactPage() {return "contact.html";}

    @GetMapping("/about.html")
    public String showAboutUsPage() {return "about.html";}

    @GetMapping("/product-details.html")
    public String showProductDetails() {return "product-details.html";}


}
