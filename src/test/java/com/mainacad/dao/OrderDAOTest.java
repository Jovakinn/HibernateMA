package com.mainacad.dao;

import com.mainacad.model.Cart;
import com.mainacad.model.Item;
import com.mainacad.model.Order;
import com.mainacad.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrderDAOTest {

    @Test
    void testSaveAndFindOneAndDelete() {
        Item item = new Item("12345", "Perforator",1000 );
        ItemDAO.save(item);

        User user = new User("testLogin", "123456", "Max", "Khodakov");
        UserDAO.save(user);

        Cart cart = new Cart( 1l, false, user);
        CartDAO.save(cart);

        Order order = new Order(item, 1, cart);
        Order savedOrder = OrderDAO.save(order);

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());

        Order dbOrder = OrderDAO.findOneById(savedOrder.getId());
        assertNotNull(dbOrder);

        List<Order> checkedOrderByCart = OrderDAO.findByCart(cart.getId());
        assertNotNull(checkedOrderByCart);

        OrderDAO.delete(savedOrder);

        dbOrder = OrderDAO.findOneById(savedOrder.getId());
        assertNull(dbOrder);
    }
}