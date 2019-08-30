package com.mainacad.dao;

import com.mainacad.model.Cart;
import com.mainacad.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartDAOTest {

    @Test
    void testSaveAndFindOneAndDelete() {
        User user = new User("Jovvakinn","12345", "Max", "Khodakov");
        UserDAO.save(user);

        Cart cart = new Cart( 1l, false, user);
        Cart savedCart = CartDAO.save(cart);

        assertNotNull(savedCart);
        assertNotNull(savedCart.getId());

        Cart dbCart = CartDAO.findOneById(savedCart.getId());
        assertNotNull(dbCart);

        CartDAO.close(savedCart.getId());

        dbCart = CartDAO.findOneById(savedCart.getId());
        assertNull(dbCart);
    }

    @Test
    void testFindByCartAndUser() {
        User user = new User("Jovvakinn","12345", "Max", "Khodakov");
        UserDAO.save(user);

        Cart cart = new Cart( 1l, false, user);
        Cart savedCart = CartDAO.save(cart);

        List<Cart> carts = CartDAO.findOpenCartByUser(user.getId());
        assertNotNull(carts.isEmpty());

        CartDAO.close(savedCart.getId());
    }
}