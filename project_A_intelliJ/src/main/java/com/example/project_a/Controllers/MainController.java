package com.example.project_a.Controllers;

import com.example.project_a.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("")
    public String showHomePage() {return "index-2.html";}

    @GetMapping("/home")
    public String showIndexPage() {return "index-2.html";}
    
    @GetMapping("/shop")
    public String showProductPage() {return "shop-list.html";}

    @GetMapping("/blog")
    public String showBlogPage() {return "blog.html";}

    @GetMapping("/contact")
    public String showContactPage() {return "contact.html";}

    @GetMapping("/about")
    public String showAboutUsPage() {return "about.html";}

    @GetMapping("/product-details")
    public String showProductDetails() {return "product-details.html";}

    @GetMapping("/404")
    public String showPageNotFound() {return "404.html";}

    @GetMapping("/account")
    public String showAccount() {return "account.html";}

    @GetMapping("/blog-details-audio")
    public String showBlogDetailsAudio() {return "blog-details-audio.html";}

    @GetMapping("/blog-details")
    public String showBlogDetails() {return "blog-details.html";}

    @GetMapping("/blog-details-blockquote")
    public String showblogdetailsblockquote() {return "blog-details-blockquote.html";}

    @GetMapping("/cart")
    public String showCart() {return "cart.html";}

    @GetMapping("/checkout")
    public String showCheckout() {return "checkout.html";}

    @GetMapping("/compare")
    public String showCompare() {return "compare.html";}

    @GetMapping("/forgot-password")
    public String ShowPageForgotPassword() {return "forgot-password.html";}

    @GetMapping("/login")
    public String ShowPageLogin() {return "login.html";}

    @GetMapping("/wishlist")
    public String ShowPageWishlist() {return "/wishlist.html";}
    
}
