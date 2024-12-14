package com.example.project_a.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("")
    public String showHomePage() {return "shop/index-2";}

    @GetMapping("/home")
    public String showIndexPage() {return "shop/index-2";}
    
    @GetMapping("/shop")
    public String showProductPage() {return "shop/shop-list";}

    @GetMapping("/blog")
    public String showBlogPage() {return "shop/blog";}

    @GetMapping("/contact")
    public String showContactPage() {return "shop/contact";}

    @GetMapping("/about")
    public String showAboutUsPage() {return "shop/about";}

    @GetMapping("/product-details")
    public String showProductDetails() {return "shop/product-details";}

    @GetMapping("/404")
    public String showPageNotFound() {return "shop/404";}

    @GetMapping("/account")
    public String showAccount() {return "shop/account";}

    @GetMapping("/blog-details-audio")
    public String showBlogDetailsAudio() {return "shop/blog-details-audio";}

    @GetMapping("/blog-details")
    public String showBlogDetails() {return "shop/blog-details";}

    @GetMapping("/blog-details-blockquote")
    public String showblogdetailsblockquote() {return "shop/blog-details-blockquote";}

    @GetMapping("/cart")
    public String showCart() {return "shop/cart";}

    @GetMapping("/checkout")
    public String showCheckout() {return "shop/checkout";}

    @GetMapping("/compare")
    public String showCompare() {return "shop/compare";}

//    @GetMapping("/forgot-password")
//    public String ShowPageForgotPassword() {return "forgot-password";}

    @GetMapping("/login")
    public String ShowPageLogin() {return "shop/login";}

    @GetMapping("/wishlist")
    public String ShowPageWishlist() {return "shop/wishlist";}

    @GetMapping("/admin")
    public String ShowPageAdmin() {return "admin/index";}

    @GetMapping("/admin/home")
    public String ShowPageAdminHome() {return "admin/index";}

    @GetMapping("/admin/category/add")
    public String ShowPageAdminCategoryAdd() {return "admin/category-add";}

    @GetMapping("/admin/category/list")
    public String ShowPageAdminCategoryList() {return "admin/category-list";}

    @GetMapping("/admin/category/edit")
    public String ShowPageAdminCategoryEdit() {return "admin/category-edit";}
}
