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

    @GetMapping("/blog.html")
    public String showBlogPage() {return "blog.html";}

    @GetMapping("/contact.html")
    public String showContactPage() {return "contact.html";}

    @GetMapping("/about.html")
    public String showAboutUsPage() {return "about.html";}

    @GetMapping("/product-details.html")
    public String showProductDetails() {return "product-details.html";}

    @GetMapping("/404.html")
    public String showPageNotFound() {return "404.html";}

    @GetMapping("/account.html")
    public String showAccount() {return "account.html";}

    @GetMapping("/blog-details-audio.html")
    public String showBlogDetailsAudio() {return "blog-details-audio.html";}

    @GetMapping("/blog-details.html")
    public String showBlogDetails() {return "blog-details.html";}

    @GetMapping("/blog-details-blockquote.html")
    public String showblogdetailsblockquote() {return "blog-details-blockquote.html";}

    @GetMapping("/cart.html")
    public String showCart() {return "cart.html";}

    @GetMapping("/checkout.html")
    public String showCheckout() {return "checkout.html";}

    @GetMapping("/compare.html")
    public String showCompare() {return "compare.html";}

    @GetMapping("/forgot-password.html")
    public String ShowPageForgotPassword() {return "forgot-password.html";}

    @GetMapping("/login.html")
    public String ShowPageLogin() {return "login.html";}

    @GetMapping("/register.html")
    public String ShowPageRegister() {return "register.html";}

    @GetMapping("/wishlist.html")
    public String ShowPageWishlist() {return "wishlist.html";}

}
