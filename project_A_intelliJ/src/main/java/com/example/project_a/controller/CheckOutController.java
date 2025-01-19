package com.example.project_a.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckOutController {
    @GetMapping("/checkout")
    public String showCheckout() {

        return "shop/checkout";
    }
}
