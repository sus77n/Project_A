package com.example.project_a.controller;

import com.example.project_a.model.Cart;
import com.example.project_a.model.Product;
import com.example.project_a.model.User;
import com.example.project_a.service.CartService;
import com.example.project_a.service.CategoryService;
import com.example.project_a.service.ProductService;
import com.example.project_a.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CartController {
    @Autowired
    private CartService service;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;


    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session,@AuthenticationPrincipal User user) {
//        List<Cart> cartList = (List<Cart>) session.getAttribute("cartList");
        List<Cart> cartList = cartService.getCartsByUserId(user.getId());
        for (Cart cart : cartList) {
            System.out.println(cart.getProduct().getName());
        }
        if (cartList == null) {
            cartList = new ArrayList<>();
        }

        BigDecimal total = cartList.stream()
                .map(Cart::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum all BigDecimal values

        for (Cart cart : cartList) {
            if(cart.getId() == null) {
                service.save(cart);
            }
        }
        model.addAttribute("carts", cartList);
        model.addAttribute("total", total);

        return "shop/cart"; // Make sure this points to your Thymeleaf cart page
    }

    @GetMapping("/cart/delete")
    public String deleteCart(@RequestParam("id") String id , RedirectAttributes ra, @AuthenticationPrincipal User user) {
        List<Cart> cartList = cartService.getCartsByUserId(user.getId());
        service.deleteCartById(Integer.parseInt(id));
        cartList.removeIf(item -> item.getId().equals(Integer.parseInt(id)));
        ra.addFlashAttribute("message", "The Cart has been deleted successfully.");
        return "redirect:/cart";
    }

    @PostMapping("/cart/add")
    @ResponseBody
    public Map<String, Object> addToCart(@RequestBody Map<String, Integer> request, @AuthenticationPrincipal User user) {
        Integer productId = request.get("id");
        Integer quantity = request.get("quantity");

        Map<String, Object> response = new HashMap<>();
        System.out.println(quantity);
        if (quantity == null || quantity <= 0) {
            if (quantity == null){
                quantity = 1;
            }else {
                response.put("ErrorMessage","Quantity is 0");
                return response;
            }
        }

        if (productId == null) {
            throw new IllegalArgumentException("Product ID is required");
        }

        Product product = productService.findProductById(productId);
        if (product == null) {
            response.put("ErrorMessage", "Product not found!");
            return response;
        }

        List<Cart> cartList = cartService.getCartsByUserId(user.getId());

        Cart existingCartItem = cartList.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            // Check stock before updating quantity
            if (existingCartItem.getQuantity() + quantity > product.getInStock()) {
                response.put("ErrorMessage", "Insufficient stock! Only " + product.getInStock() + " available.");
            } else if (existingCartItem.getQuantity() + quantity > 100) {
                response.put("ErrorMessage", "You cannot order more than 100 items!");
            } else {
                existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
                cartService.updateCart(existingCartItem.getId() + "", existingCartItem);
            }
        } else {
            // Create new cart item if it does not exist
            if (quantity <= product.getInStock()) {
                Cart newCartItem = new Cart();
                newCartItem.setProduct(product);
                newCartItem.setQuantity(quantity);
                newCartItem.setUser(user);
                service.save(newCartItem);
            } else {
                response.put("ErrorMessage", "Insufficient stock! Only " + product.getInStock() + " available.");
            }
        }

        // Refresh cart list after update
        cartList = cartService.getCartsByUserId(user.getId());

        // Calculate total cart items
        int totalCartItems = cartList.stream().mapToInt(Cart::getQuantity).sum();

        response.put("cartSize", totalCartItems);
        response.put("cartItems", cartList.stream()
                .map(cartItem -> {
                    Map<String, Object> itemMap = new HashMap<>();
                    itemMap.put("productName", cartItem.getProduct().getName());
                    itemMap.put("quantity", cartItem.getQuantity());
                    itemMap.put("price", cartItem.getQuantity() * cartItem.getProduct().getPrice());
                    itemMap.put("thumbNail", cartItem.getProduct().getThumbnail().getFilePath());
                    return itemMap;
                })
                .collect(Collectors.toList()));

        return response;
    }


    @PostMapping("/cart/update")
    @ResponseBody
    public Map<String, Object> updateCart(@RequestParam("id") Integer cartId, @RequestParam("quantity") Integer quantity, @AuthenticationPrincipal User user) {
        List<Cart> cartList = cartService.getCartsByUserId(user.getId());
        Map<String, Object> response = new HashMap<>();
        if (cartList != null) {
            for (Cart cart : cartList) {
                if (cart.getId().equals(cartId)) {
                    System.out.println("checkQuantity");
                    if (quantity > cart.getProduct().getInStock()) {
                        System.out.println("Check error quantity");
                        response.put("ErrorMessage", "Insufficient stock! Only " + cart.getProduct().getInStock() + " available.");
                        break;
                    }else {
                        System.out.println(quantity);
                        cart.setQuantity(quantity);
                        cartService.updateCart(cartId+"", cart);
                        break;
                    }

                }
            }
        }



        BigDecimal total = cartList.stream()
                .map(Cart::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal subtotal = cartList.stream()
                .filter(cart -> cart.getId().equals(cartId))
                .findFirst()
                .map(Cart::getTotal)
                .orElse(BigDecimal.ZERO);


        response.put("subtotal", subtotal);
        response.put("total", total);
        return response;
    }


    @GetMapping("/cart/data")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCartData(HttpSession session,  @AuthenticationPrincipal User user) {
        List<Cart> cartList = cartService.getCartsByUserId(user.getId());


        //Check if cartList is null before using it
        if (cartList == null) {
            cartList = new ArrayList<>();
        }
        int cartSize = 0;
        for (Cart cart : cartList) {
           cartSize += cart.getQuantity();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("cartSize", cartSize);
        response.put("cartItems", cartList.stream().map(cartItem -> {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("productName", cartItem.getProduct().getName());
            itemMap.put("quantity", cartItem.getQuantity());
            itemMap.put("price", cartItem.getQuantity() * cartItem.getProduct().getPrice());
            itemMap.put("thumbNail", cartItem.getProduct().getThumbnail().getFilePath());
            return itemMap;
        }).collect(Collectors.toList()));

        return ResponseEntity.ok(response);
    }
}
