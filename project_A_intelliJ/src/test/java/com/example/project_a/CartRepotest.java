package com.example.project_a;
import com.example.project_a.model.Cart;
import com.example.project_a.model.Category;
import com.example.project_a.model.Product;
import com.example.project_a.model.User;
import com.example.project_a.repository.CartRepository;
import com.example.project_a.repository.CategoryRepository;
import com.example.project_a.repository.ProductRepository;
import com.example.project_a.repository.UserRepository;
import com.example.project_a.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)

public class CartRepotest {

    @Autowired private CartRepository repo;
    @Autowired private UserRepository userRepo;
    @Autowired private ProductRepository productRepo;


    @Test
    public void AddNewCart() {

        Cart cart = new Cart();

        Optional<User> opUser = userRepo.findById(1);
        User user = opUser.get();
        System.out.println(user.getId());
        Optional<Product> opProduct = productRepo.findById(2);
        Product product = opProduct.get();

        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(2);


        Cart savedCart = repo.save(cart);
        Assertions.assertNotNull(savedCart);
        System.out.println(product.getProduct_id());
        System.out.println(product.getName());


    }

//    @Test
//    public void testListAllCategories() {
//        Iterable<Category> categories = repo.findAll();
//        Assertions.assertTrue(categories.iterator().hasNext(), "Users collection should contain at least one user");
//
//        for (Category category : categories) {
//            System.out.println(category);
//        }
//    }

//    @Test
//    public void testUdateCategory() {
//        Integer CategoryID = 1;
//        Optional<Category> optionalCategory = repo.findById(CategoryID);
//        Category category = optionalCategory.get();
//        category.setStatus("Disable");
//
//        repo.save(category);
//    }
//    @Test
//    public void testGetCategory() {
//        Integer CategoryID = 2;
//        Optional<Category> optionalCategory = repo.findById(CategoryID);
//        Assertions.assertTrue(optionalCategory.isPresent(), "Category should be present");
//        Category category = optionalCategory.get();
//        System.out.println(category.getId());
//        System.out.println(category.getCategoryName());
//    }

//    @Test
//    public void testDeleteCategory() {
//        Integer CategoryID = 1;
//        repo.deleteById(CategoryID);
//        Optional deletedUser = repo.findById(CategoryID);
//        Assertions.assertFalse(deletedUser.isPresent() , "User should be null");
//    }

}
