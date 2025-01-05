package com.example.project_a.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("")
    public String showHomePage() {return "shop/index-2";}

    @GetMapping("/home")
    public String showIndexPage() {return "shop/index-2";}
    
//    @GetMapping("/shop")
//    public String showProductPage() {return "shop/shop-list";}

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

    @GetMapping("/forgot-password")
    public String ShowPageForgotPassword() {return "shop/forgot-password";}

    @GetMapping("/login")
    public String ShowPageLogin() {return "shop/login";}

    @GetMapping("/register")
    public String ShowPageRegister() {return "shop/register";}

    @GetMapping("/wishlist")
    public String ShowPageWishlist() {return "shop/wishlist";}

    @GetMapping("/quickview")
    public String ShowPageQuickreview() {return "shop/quickviewProduct";}

//   Admin mapping
    @GetMapping("/admin")
    public String ShowPageAdmin(Model model) {
        model.addAttribute("pageTitle", "Welcome" );
        return "admin/index";}

    @GetMapping("/admin/home")
    public String ShowPageAdminHome(Model model) {
        model.addAttribute("pageTitle", "Welcome" );
        return "admin/index";}

//    @GetMapping("/admin/category/list")
//    public String ShowPageAdminCategoryList() {return "admin/category-list";}

//    @GetMapping("/admin/category/edit")
//    public String ShowPageAdminCategoryEdit() {return "admin/category-edit";}

    @GetMapping("/admin/product/list")
    public String ShowPageAdminProduct(Model model) {
        model.addAttribute("pageTitle", "Product" );
        return "admin/product-list";
    }

//    @GetMapping("/admin/product/grid")
//    public String ShowPageAdminProductGrid() {return "admin/product-grid";}

//    @GetMapping("/admin/product/add")
//    public String ShowPageAdminProductAdd() {return "admin/product-add";}

    @GetMapping("/admin/product/details")
    public String ShowPageAdminProductDetails(Model model) {
        model.addAttribute("pageTitle", "Product Details" );
        return "admin/product-details";}

    @GetMapping("/admin/product/edit")
    public String ShowPageAdminProductEdit(Model model) {
        model.addAttribute("pageTitle", "Product Edit" );
        return "admin/product-edit";}

    @GetMapping("/admin/order/details")
    public String ShowPageAdminOrderDetail(Model model) {
        model.addAttribute("pageTitle", "Order Details" );
        return "admin/order-detail";}

    @GetMapping("/admin/order/list")
    public String ShowPageAdminOrderList(Model model) {
        model.addAttribute("pageTitle", "Order" );
        return "admin/orders-list";}

    @GetMapping("/admin/invoice/list")
    public String ShowPageAdminInvoiceList(Model model) {
        model.addAttribute("pageTitle", "Invoice" );
        return "admin/invoice-list";}

    @GetMapping("/admin/invoice/details")
    public String ShowPageAdminOrderDetails(Model model) {
        model.addAttribute("pageTitle", "Invoice Details" );
        return "admin/invoice-details";}

    @GetMapping("/admin/invoice/add")
    public String ShowPageAdminInvoiceAdd(Model model) {
        model.addAttribute("pageTitle", "Invoice Add" );
        return "admin/invoice-add";}

    @GetMapping("/admin/settings")
    public String ShowPageAdminSettings(Model model) {
        model.addAttribute("pageTitle", "Settings" );
        return "admin/settings";}

    @GetMapping("/admin/profile")
    public String ShowPageAdminProfile(Model model) {
        model.addAttribute("pageTitle", "Profile" );
        return "admin/pages-profile";}
}
