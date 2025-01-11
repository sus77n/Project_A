package com.example.project_a;

import com.example.project_a.model.Category;
import com.example.project_a.repository.CategoryRepository;
import com.example.project_a.model.Product;
import com.example.project_a.repository.ProductRepository;
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


public class ProductRepositoryTest {

    @Autowired private ProductRepository repo;
    @Autowired private CategoryRepository Caterepo;
    @Test
    public void AddNewProduct() {
        Product pro = new Product();
        pro.setName("ShoeCu3");
        pro.setSummary("ShoeFe");
        pro.setDescription("ShoeFe");
        pro.setPrice(7.0);
        pro.setInStock(7);

        Integer CategoryID = 1;
        Optional<Category> optionalCategory = Caterepo.findById(CategoryID);

        pro.setCategory(optionalCategory.get());
        Product savedProduct = repo.save(pro);
        Assertions.assertNotNull(savedProduct);
        Assertions.assertTrue(savedProduct.getProduct_id() > 0, "Product ID should be greater than 0");
    }

    @Test
    public void testListAllProduct() {
        Iterable<Product> pros = repo.findAll();
        Assertions.assertTrue(pros.iterator().hasNext(), "Products collection should contain at least one Product");
        for (Product pro : pros) {
            System.out.println(pro);
        }
    }

    public void testUdateProduct() {
        Integer proID = 1;
        Optional<Product> optionalProduct = repo.findById(proID);
        Product pro = optionalProduct.get();
        pro.setName("helo");
        repo.save(pro);

        Product updatedPro = repo.findById(proID).get();
        Assertions.assertEquals("helo", updatedPro.getName(), "product Name should be 'Helo'");
    }


    @Test
    public void testGetProduct() {
        Integer proID = 1;
        Optional<Product> optionalProduct = repo.findById(proID);
        Assertions.assertTrue(optionalProduct.isPresent(), "User should be present");
        Product pro = optionalProduct.get();
        System.out.println(pro);
    }

    @Test
    public void testDeleteProduct() {
        Integer proID = 1;
        repo.deleteById(proID);
        Optional deletedProduct = repo.findById(proID);
        Assertions.assertFalse(deletedProduct.isPresent() , "User should be null");
    }
}
