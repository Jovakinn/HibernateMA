package com.mainacad.dao;

import com.mainacad.model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class ItemDAO {

    public static Item save(Item item){
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();
        Integer id = (Integer) session.save(item);
        item.setId(id);

        session.getTransaction().commit();
        session.close();

        return item;
    }

    public static Item update(Item item){
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();
        session.update(item);

        session.getTransaction().commit();
        session.close();

        return item;
    }

    public static Item findOneById(Integer id){
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();
        Item item = session.find(Item.class, id);

        session.getTransaction().commit();
        session.close();

        return item;
    }

    public static List<Item> findAll(){
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();
        String sql = "SELECT * FROM items";
        List<Item> items = session.createNativeQuery(sql, Item.class).getResultList();

        session.getTransaction().commit();
        session.close();

        return items;
    }

    public static Item findOneByItemCode(String itemCode){
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();
        Item item = session.find(Item.class, itemCode);

        session.getTransaction().commit();
        session.close();

        return item;
    }

    public static void delete(Item item){
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();
        session.delete(item);

        session.getTransaction().commit();
        session.close();
    }

    public static List<Item> getSumOfAllOrdersByUserIdAndPeriod(Integer userId, Long from, Long to){
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();

        String sql = "SELECT SUM(i.price*o.amount) " + "FROM items i " +
                "JOIN orders o ON o.item_id = i.id " +
                "JOIN carts c ON o.cart_id = c.id " +
                "WHERE c.user_id=2 AND " +
                "c.creation_time>1564088300000 AND " +
                "c.creation_time<1564088500000 AND " +
                "c.closed=true";

        NativeQuery query = session.createNativeQuery(sql, Item.class);
        query.setParameter(1, userId);
        query.setParameter(2, from);
        query.setParameter(3, to);

        List<Item> items = query.getResultList();

        session.getTransaction().commit();
        session.close();

        return items;
    }
}
