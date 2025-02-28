package com.example.project_a.controller;

import com.example.project_a.model.Category;
import com.example.project_a.model.Media;
import com.example.project_a.model.Product;
import com.example.project_a.service.CategoryService;
import com.example.project_a.service.MediaService;
import com.example.project_a.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ProductController {
    @Autowired
    private ProductService service;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MediaService mediaService;

    @GetMapping("/admin/product/list")
    public String ShowPageAdminProduct(Model model) {
        List<Product> products = service.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("pageTitle", "Product");
        return "admin/product-list";
    }

    @GetMapping("/admin/product/add")
    public String showProductForm(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("product", new Product());
        return "admin/product-add";
    }

    @PostMapping("/admin/product/save")
    public String saveProduct(Product product,
                              @RequestParam String categoryId,
                              @RequestParam String thumbnailName,
                              @RequestParam String sliderImages,
                              RedirectAttributes ra) {

        Category category = categoryService.findCategoryById(Integer.parseInt(categoryId));
        product.setCategory(category);

        List<String> imagePaths = mediaService.getListOfMediaInJson(sliderImages);

        // Convert image paths to Media objects and associate them with the product
        for (String imagePath : imagePaths) {
            Media media = mediaService.constructMedia(mediaService.getFileUrl(imagePath), "Product slider", "Product slider");
            media.setProduct(product);
            product.getProductSliders().add(media);
        }

        Media thumbnail = null;
        if (!thumbnailName.isEmpty()) {
            thumbnail = mediaService.constructMedia(mediaService.getFileUrl(thumbnailName), "Product thumbnail", "Thumbnail");
            product.setThumbnail(thumbnail);
        }

        product.setProductSliders(product.getProductSliders());
        service.save(product);

        ra.addFlashAttribute("message", "The product has been saved successfully.");
        return "redirect:/admin/product/list";
    }

    @GetMapping("/admin/product/details")
    public String ShowPageAdminProductDetails(@RequestParam String productId, Model model) {
        Product product = service.findProductById(Integer.parseInt(productId));
        model.addAttribute("product", product);
        model.addAttribute("pageTitle", "Product Details");
        return "admin/product-details";
    }

    @GetMapping("/admin/product/edit")
    public String editProduct(@RequestParam String productId, Model model) {

        Product product = service.findProductById(Integer.parseInt(productId));
        List<Category> categories = categoryService.getAllCategories();

        Media thumbnail = product.getThumbnail();
        List<String> sliderImagePaths = product.getProductSliders()
                .stream()
                .map(slider -> mediaService.getFileName(slider.getFilePath()))
                .collect(Collectors.toList());

        product.setProductSliders(null);

        // Convert list to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String sliderImagesJson = "[]"; // Default empty array

        try {
            sliderImagesJson = objectMapper.writeValueAsString(sliderImagePaths);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Handle error properly
        }

        model.addAttribute("sliderImagesJson", sliderImagesJson);
        model.addAttribute("thumbnail", mediaService.getFileName(thumbnail.getFilePath()));
        model.addAttribute("categories", categories);
        model.addAttribute("product", product);
        model.addAttribute("pageTitle", "Product Edit");

        return "admin/product-edit";
    }

    @PostMapping("/admin/product/update")
    public String editProduct(Product product,
                              @RequestParam String categoryId,
                              @RequestParam String thumbnailName,
                              @RequestParam String sliderImages,
                              RedirectAttributes ra) {

        Media thumbnail = null;
        if (!thumbnailName.isEmpty()) {
            if (product.getThumbnail() != null){
                mediaService.removeByName(mediaService.getFileName(product.getThumbnail().getFilePath()));
            }
            thumbnail = mediaService.constructMedia(mediaService.getFileUrl(thumbnailName), "Product thumbnail", "Thumbnail");
        }

        List<String> imagePaths = mediaService.getListOfMediaInJson(sliderImages);

        // Convert image paths to Media objects and associate them with the product
        for (String imagePath : imagePaths) {
            Media media = mediaService.constructMedia(mediaService.getFileUrl(imagePath), "Product slider", "Product slider");
            media.setProduct(product);
            product.getProductSliders().add(media);
        }

        Category category = categoryService.findCategoryById(Integer.parseInt(categoryId));

        product.setCategory(category);
        product.setThumbnail(thumbnail);

        service.updateProduct(product);

        ra.addFlashAttribute("message", "The product has been edited successfully.");
        return "redirect:/admin/product/list";
    }

//    @PostMapping("/admin/product/delete")
//    public String deleteProduct(@RequestParam String productId, RedirectAttributes ra) {
//        service.deleteProductById(Integer.parseInt(productId));
//        return "redirect:/admin/product/list";
//    }

    @PostMapping("/admin/product/status/change")
    public String changeStatus(@RequestParam String productId, RedirectAttributes ra) {
        Product product = service.findProductById(Integer.parseInt(productId));
        product.setStatus(product.getStatus().equals("Active") ? "Inactive" : "Active");
        service.save(product);
        ra.addFlashAttribute("message", "The product has been changed successfully.");
        return "redirect:/admin/product/list";
    }

    @GetMapping("/shop")
    public String showProductPage(
            @RequestParam(value = "categories", required = false) List<Long> categoryIds,
            Model model) {

        List<Category> categories = categoryService.getAllCategories();
        List<Product> products;

        if (categoryIds != null && !categoryIds.isEmpty()) {
            products = service.getProductsByCategoryIds(categoryIds);
        } else {
            products = service.getAllProducts();
        }

        model.addAttribute("categories", categories);
        model.addAttribute("products", products);

        return "shop/shop-list";
    }


    @GetMapping("/filter-products")
    public String filterProducts(
            @RequestParam(value = "categories", required = false) List<Long> categoryIds,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "sort", required = false) String sort,
            Model model) {

        List<Product> filteredProducts = service.getAllProducts();

        if (categoryIds != null && !categoryIds.isEmpty()) {
            filteredProducts = service.getProductsByCategoryIds(categoryIds);
        }

        if (minPrice != null && maxPrice != null) {
            double finalMinPrice = minPrice;
            double finalMaxPrice = maxPrice;
            filteredProducts = filteredProducts.stream()
                    .filter(p -> p.getPrice() >= finalMinPrice && p.getPrice() <= finalMaxPrice)
                    .collect(Collectors.toList());
        }

        if (sort != null) {
            switch (sort) {
                case "nameAsc":
                    filteredProducts.sort(Comparator.comparing(Product::getName));
                    break;
                case "nameDesc":
                    filteredProducts.sort(Comparator.comparing(Product::getName).reversed());
                    break;
                case "priceAsc":
                    filteredProducts.sort(Comparator.comparing(Product::getPrice));
                    break;
                case "priceDesc":
                    filteredProducts.sort(Comparator.comparing(Product::getPrice).reversed());
                    break;
            }
        }

        model.addAttribute("products", filteredProducts);
        model.addAttribute("productsCount", filteredProducts.size());

        return "shop/shop-list :: productList";
    }

    @GetMapping("/home")
    public String showIndexPage(Model model) {
        List<Product> products = service.getAllProducts();
        List<Product> firstGroup = products.stream().limit(4).toList();
        List<Product> secondGroup = products.size() > 4 ? products.stream().skip(4).limit(4).toList() : Collections.emptyList();
        List<Category> categories = categoryService.getAllCategories();
        List<Category> displayedCategories = categories.stream().limit(3).toList();

        Map<Category, List<Product>> categoryProducts = new LinkedHashMap<>();
        for (Category category : displayedCategories) {
            List<Product> categoryProductsList = service.getProductsByCategoryIds(Collections.singletonList(category.getId().longValue())
                    )
                    .stream().limit(4).toList();
            categoryProducts.put(category, categoryProductsList);
        }

        model.addAttribute("products", products);
        model.addAttribute("firstGroup", firstGroup);
        model.addAttribute("secondGroup", secondGroup);
        model.addAttribute("categoryProducts", categoryProducts);  // Map<Category, List<Product>>
        model.addAttribute("categories", categories);
        model.addAttribute("displayedCategories", displayedCategories);
        return "shop/index-2";
    }

    @GetMapping("")
    public String showHomePage(Model model) {
        List<Product> products = service.getAllProducts();
        List<Product> firstGroup = products.stream().limit(4).toList();
        List<Product> secondGroup = products.size() > 4 ? products.stream().skip(4).limit(4).toList() : Collections.emptyList();
        List<Category> categories = categoryService.getAllCategories();
        List<Category> displayedCategories = categories.stream().limit(3).toList();

        Map<Category, List<Product>> categoryProducts = new LinkedHashMap<>();
        for (Category category : displayedCategories) {
            List<Product> categoryProductsList = service.getProductsByCategoryIds(Collections.singletonList(category.getId().longValue())
                    )
                    .stream().limit(4).toList();
            categoryProducts.put(category, categoryProductsList);
        }

        model.addAttribute("products", products);
        model.addAttribute("firstGroup", firstGroup);
        model.addAttribute("secondGroup", secondGroup);
        model.addAttribute("categoryProducts", categoryProducts);  // Map<Category, List<Product>>
        model.addAttribute("categories", categories);
        model.addAttribute("displayedCategories", displayedCategories);
        return "shop/index-2";
    }


    @GetMapping("/product-details")
    public String showProductDetails(@RequestParam String productId, Model model) {
        Product product = service.findProductById(Integer.parseInt(productId));
        List<Product> thisCateProducts = service.getAllProducts();
        Long cateID = Long.valueOf(product.getCategory().getId());

        thisCateProducts = service.getProductsByCategoryIds(new ArrayList<>() {
            {
                add(cateID);
            }
        });
        thisCateProducts = thisCateProducts.subList(Math.max(thisCateProducts.size() - 4, 0), thisCateProducts.size());

        model.addAttribute("product", product);
        model.addAttribute("thisCateProducts", thisCateProducts);
        return "shop/product-details";
    }
}
