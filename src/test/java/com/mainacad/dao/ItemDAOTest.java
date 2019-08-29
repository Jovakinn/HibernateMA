package com.mainacad.dao;

import com.mainacad.model.Item;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemDAOTest {

    @Test
    void testSaveAndFindOneAndDelete() {
        Item item = new Item( "123456", "Max", 1000);

        Item savedItem = ItemDAO.save(item);
        assertNotNull(savedItem);
        assertNotNull(savedItem.getId());

        Item dbItem = ItemDAO.findOneById(savedItem.getId());
        assertNotNull(dbItem);

        ItemDAO.delete(savedItem);

        dbItem = ItemDAO.findOneById(savedItem.getId());
        assertNull(dbItem);
    }

    @Test
    void testFindByLoginAndPassword() {
        Item item = new Item( "123456", "Max", 1000);

        Item savedItem = ItemDAO.save(item);

        List<Item> items = ItemDAO.findByItemCode(item.getItemCode());
        assertTrue(!items.isEmpty());

        assertEquals(savedItem.getName(), items.get(0).getName());

        ItemDAO.delete(savedItem);
    }
}