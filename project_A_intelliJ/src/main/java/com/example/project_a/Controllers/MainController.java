package com.example.project_a.Controllers;

import com.example.project_a.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("")
    public String showHomePage() {return "index-2";}

    @GetMapping("/home")
    public String showIndexPage() {return "index-2";}
    
    @GetMapping("/shop")
    public String showProductPage() {return "shop-list";}

    @GetMapping("/blog")
    public String showBlogPage() {return "blog";}

    @GetMapping("/contact")
    public String showContactPage() {return "contact";}

    @GetMapping("/about")
    public String showAboutUsPage() {return "about";}

    @GetMapping("/product-details")
    public String showProductDetails() {return "product-details";}

    @GetMapping("/404")
    public String showPageNotFound() {return "404";}

    @GetMapping("/account")
    public String showAccount() {return "account";}

    @GetMapping("/blog-details-audio")
    public String showBlogDetailsAudio() {return "blog-details-audio";}

    @GetMapping("/blog-details")
    public String showBlogDetails() {return "blog-details";}

    @GetMapping("/blog-details-blockquote")
    public String showblogdetailsblockquote() {return "blog-details-blockquote";}

    @GetMapping("/cart")
    public String showCart() {return "cart";}

    @GetMapping("/checkout")
    public String showCheckout() {return "checkout";}

    @GetMapping("/compare")
    public String showCompare() {return "compare";}

    @GetMapping("/forgot-password")
    public String ShowPageForgotPassword() {return "forgot-password";}

    @GetMapping("/login")
    public String ShowPageLogin() {return "login";}

    @GetMapping("/wishlist")
    public String ShowPageWishlist() {return "wishlist";}
    

}
