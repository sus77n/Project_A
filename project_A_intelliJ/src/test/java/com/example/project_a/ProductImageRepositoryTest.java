package com.example.project_a;

import com.example.project_a.model.Product;
import com.example.project_a.model.ProductImage;
import com.example.project_a.repository.ProductImageRepository;
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


public class ProductImageRepositoryTest {

    @Autowired private ProductImageRepository repo;
    @Autowired private ProductRepository proRepo;

    @Test
    public void AddNewProductImage() {
        ProductImage productImage = new ProductImage();
        productImage.setImageURL("../asset/logo3");
        Product product = getProductByID(2);
        productImage.setProduct(product);
        ProductImage savedProduct = repo.save(productImage);
        Assertions.assertNotNull(savedProduct);
        Assertions.assertTrue(savedProduct.getProductImage_id() > 0, "ProductImage ID should be greater than 0");
    }
    //Helper function for AddNewProductImage
    public Product getProductByID(Integer id) {
        Integer proID = id;
        Optional<Product> optionalProduct = proRepo.findById(proID);
        Assertions.assertTrue(optionalProduct.isPresent(), "User should be present");
        Product pro = optionalProduct.get();
        return pro;
    }
    //End Helper Function



    @Test
    public void testListAllProductImage() {
        Iterable<ProductImage> pros = repo.findAll();
        Assertions.assertTrue(pros.iterator().hasNext(), "ProductImages collection should contain at least one ProductImage");

        for (ProductImage productImage : pros) {
            System.out.println(productImage);
        }
    }

//    public void testUdateProduct() {
//        Integer proID = 1;
//        Optional<Product> optionalProduct = repo.findById(proID);
//        Product pro = optionalProduct.get();
//        pro.setName("helo");
//        repo.save(pro);
//
//        Product updatedPro = repo.findById(proID).get();
//        Assertions.assertEquals("helo", updatedPro.getName(), "product Name should be 'Helo'");
//    }

    @Test
    public void testGetProductImage() {
        Integer ID = 1;
        Optional<ProductImage> optionalProductImage = repo.findById(ID);
        Assertions.assertTrue(optionalProductImage.isPresent(), "User should be present");
        ProductImage pro = optionalProductImage.get();
        System.out.println(pro);
    }

    @Test
    public void testDeleteProduct() {
        Integer ID = 1;
        repo.deleteById(ID);
        Optional deletedProductIamge = repo.findById(ID);
        Assertions.assertFalse(deletedProductIamge.isPresent() , "User should be null");
    }
}
