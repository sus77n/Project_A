package com.example.project_a;

import com.example.project_a.model.User;
import com.example.project_a.repository.UserRepository;
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
public class UserRepositoryTests {
    @Autowired private UserRepository repo;
    @Test
    public void AddNewUser() {
        User user = new User();
        user.setId(34);
        user.setUsername("New6");
        user.setPassword("456789");
        user.setGender("Meo");
//        user.setDateOfBirth("5/5/2005");
        user.setPhoneNumber("923654787");
        user.setAddress("KhuOChuot Dinh Hoa");
//        user.setEmail("absdsac@gmail.com");
        user.setStatus("Active");
        user.setRole("Admin");
        user.setCitizenId("1465131");

        User savedUser = repo.save(user);
        Assertions.assertNotNull(savedUser);
        Assertions.assertTrue(savedUser.getId() > 0, "User ID should be greater than 0");


    }

    @Test
    public void testListAllUsers() {
        Iterable<User> users = repo.findAll();
        Assertions.assertTrue(users.iterator().hasNext(), "Users collection should contain at least one user");

        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testUdateUser() {
        Integer userID = 1;
        Optional<User> optionalUser = repo.findById(userID);
        User user = optionalUser.get();
        user.setPassword("helo");

        repo.save(user);

        User updatedUser = repo.findById(userID).get();
        Assertions.assertEquals("helo", updatedUser.getPassword(), "Password should be 'Helo'");

    }
    @Test
    public void testGetUser() {
        Integer userID = 1;
        Optional<User> optionalUser = repo.findById(userID);
        Assertions.assertTrue(optionalUser.isPresent(), "User should be present");
        User user = optionalUser.get();
        System.out.println(user);
    }

    @Test
    public void testDeleteUser() {
        Integer userID = 1;
        repo.deleteById(userID);
        Optional deletedUser = repo.findById(userID);
        Assertions.assertFalse(deletedUser.isPresent() , "User should be null");
    }

}
