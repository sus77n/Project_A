package com.example.project_a;
import com.example.project_a.model.Category;
import com.example.project_a.repository.CategoryRepository;
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

public class CategoryRepositoryTests {

    @Autowired private CategoryRepository repo;
    @Test
    public void AddNewCategory() {
        Category category = new Category();
        category.setCategoryname("Tent");
        category.setStatus("ACTIVE");

        Category savedCategory = repo.save(category);
        Assertions.assertNotNull(savedCategory);
//        Assertions.assertTrue(savedCategory.getID() > 0, "User ID should be greater than 0");


    }

    @Test
    public void testListAllUsers() {
        Iterable<Category> categories = repo.findAll();
        Assertions.assertTrue(categories.iterator().hasNext(), "Users collection should contain at least one user");

        for (Category category : categories) {
            System.out.println(category);
        }
    }

    @Test
    public void testUdateCategory() {
        Integer CategoryID = 1;
        Optional<Category> optionalCategory = repo.findById(CategoryID);
        Category category = optionalCategory.get();
        category.setStatus("Disable");

        repo.save(category);
    }
    @Test
    public void testGetCategory() {
        Integer CategoryID = 1;
        Optional<Category> optionalCategory = repo.findById(CategoryID);
        Assertions.assertTrue(optionalCategory.isPresent(), "User should be present");
        Category category = optionalCategory.get();
        System.out.println(category);
    }

    @Test
    public void testDeleteCategory() {
        Integer CategoryID = 1;
        repo.deleteById(CategoryID);
        Optional deletedUser = repo.findById(CategoryID);
        Assertions.assertFalse(deletedUser.isPresent() , "User should be null");
    }

}
