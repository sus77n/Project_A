package com.example.project_a.controller;

import com.example.project_a.model.Order;
import com.example.project_a.model.Product;
import com.example.project_a.model.User;
import com.example.project_a.service.OrderService;
import com.example.project_a.service.ProductService;
import com.example.project_a.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService UserService;

    @GetMapping("/blog")
    public String showBlogPage() {return "shop/blog";}

    @GetMapping("/contact")
    public String showContactPage() {return "shop/contact";}

    @GetMapping("/about")
    public String showAboutUsPage() {return "shop/about";}

    @GetMapping("/404")
    public String showPageNotFound() {return "shop/404";}

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

//    @GetMapping("/forgot-password")
//    public String ShowPageForgotPassword() {return "shop/forgot-password";}

//    @GetMapping("/reset-password")
//    public String showResetPasswordForm(@RequestParam String token, Model model) {
//        model.addAttribute("token", token);
//        return "shop/reset-password";
//    }

    @GetMapping("/login")
    public String ShowPageLogin() {return "shop/login";}

    @GetMapping("/wishlist")
    public String ShowPageWishlist() {return "shop/wishlist";}


//   ------------------------------Admin mapping----------------------------------------

    @GetMapping("/admin")
    public String ShowPageAdmin(Model model) {
        List<Order> orders = orderService.getAllOrders();
        List<Product> products = productService.getAllProducts();
        List<User> users = UserService.getAllUsers();
        model.addAttribute("pageTitle", "Welcome" );
        model.addAttribute("listOrder", orders);
        model.addAttribute("listProduct", products);
        model.addAttribute("listUser", users);
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
        return "user-detail";
    }

    @GetMapping("/error")
    public String showErrorPage() {return "admin/pages-404";}

}
