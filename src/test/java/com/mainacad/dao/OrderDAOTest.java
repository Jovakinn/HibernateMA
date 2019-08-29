package com.mainacad.dao;

import com.mainacad.model.Cart;
import com.mainacad.model.Item;
import com.mainacad.model.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrderDAOTest {

    @Test
    void testSaveAndFindOneAndDelete() {
        Item item = new Item();
        Cart cart = new Cart();

        Order order = new Order(item, 1, cart);

        Order savedOrder = OrderDAO.save(order);
        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());

        Order dbOrder = OrderDAO.findOneById(savedOrder.getId());
        assertNotNull(dbOrder);

        OrderDAO.delete(savedOrder);

        dbOrder = OrderDAO.findOneById(savedOrder.getId());
        assertNull(dbOrder);
    }
}