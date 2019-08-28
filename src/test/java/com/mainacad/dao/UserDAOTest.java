package com.mainacad.dao;

import com.mainacad.model.User;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    @Test
    void testSaveAndFindOneAndDelete() {
        User user = new User("Jovakinn", "123456", "Max", "Khodakov");

        User savedUser = UserDAO.save(user);
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());

        User dbUser = UserDAO.findOneById(savedUser.getId());
        assertNotNull(dbUser);

        UserDAO.delete(savedUser);

        dbUser = UserDAO.findOneById(savedUser.getId());
        assertNull(dbUser);
    }

    @Test
    void testFindByLoginAndPassword() {
        User user = new User("testLogin", "123456", "Max", "Khodakov");

        User savedUser = UserDAO.save(user);

        List<User> users = UserDAO.findByLoginAndPassword("testLogin", "123456");
        assertTrue(!users.isEmpty());

        assertEquals(savedUser.getFirstName(), users.get(0).getFirstName());

        UserDAO.delete(savedUser);
    }
}