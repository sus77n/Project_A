package com.example.project_a.controller;

import com.example.project_a.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/blog")
    public String showBlogPage() {return "shop/blog";}

    @GetMapping("/contact")
    public String showContactPage() {return "shop/contact";}

    @GetMapping("/about")
    public String showAboutUsPage() {return "shop/about";}

    @GetMapping("/account")
    public String showAccount(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
            System.out.println("User is logged in: " + user.getUsername());
        } else {
            System.out.println("No authenticated user");
        }
        return "shop/account";
    }

    @GetMapping("/blog-details-audio")
    public String showBlogDetailsAudio() {return "shop/blog-details-audio";}

    @GetMapping("/blog-details")
    public String showBlogDetails() {return "shop/blog-details";}

    @GetMapping("/blog-details-blockquote")
    public String showblogdetailsblockquote() {return "shop/blog-details-blockquote";}

    @GetMapping("/orderDetail")
    public String showOrderDetail() {return "shop/order-cart";}

    @GetMapping("/compare")
    public String showCompare() {return "shop/compare";}

    @GetMapping("/forgot-password")
    public String ShowPageForgotPassword() {return "shop/forgot-password";}

    @GetMapping("/login")
    public String ShowPageLogin() {return "shop/login";}

    @GetMapping("/wishlist")
    public String ShowPageWishlist() {return "shop/wishlist";}


//   ------------------------------Admin mapping----------------------------------------

    @GetMapping("/admin")
    public String ShowPageAdmin(Model model) {
        model.addAttribute("pageTitle", "Welcome" );
        return "admin/index";}

    @GetMapping("/admin/home")
    public String ShowPageAdminHome(Model model) {
        model.addAttribute("pageTitle", "Welcome" );
        return "admin/index";
    }

    @GetMapping("/admin/order/details")
    public String ShowPageAdminOrderDetail(Model model) {
        model.addAttribute("pageTitle", "Order Details" );
        return "admin/order-detail";
    }

    @GetMapping("/admin/profile")
    public String ShowPageAdminProfile(Model model) {
        model.addAttribute("pageTitle", "Profile" );
        return "admin/pages-profile";
    }

    @GetMapping("/admin/customer/details")
    public String ShowPageAdminCustomerDetail(Model model) {
        model.addAttribute("pageTitle", "Customer Details" );
        return "admin/customer-detail";
    }
}
