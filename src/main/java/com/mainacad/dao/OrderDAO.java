package com.mainacad.dao;

import com.mainacad.model.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class OrderDAO {


    public static Order save(Order order){
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();
        Integer id = (Integer) session.save(order);
        order.setId(id);

        session.getTransaction().commit();
        session.close();

        return order;
    }


    public static Order update(Order order){
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();
        session.update(order);

        session.getTransaction().commit();
        session.close();

        return order;
    }

    public static Order findOneById(Integer id){
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();
        Order order = session.find(Order.class, id);

        session.getTransaction().commit();
        session.close();

        return order;
    }

    public static List<Order> findByCart(Integer cartId){
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();
        String sql = "SELECT * FROM orders WHERE cart_id=?";

        NativeQuery quary = session.createNativeQuery(sql, Order.class);
        quary.setParameter(1, cartId);

        List<Order> orders = quary.getResultList();

        session.getTransaction().commit();
        session.close();

        return orders;
    }

    public static void delete(Order order){
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();
        session.delete(order);

        session.getTransaction().commit();
        session.close();
    }


    public static List<Order>  findClosedOrdersByUserAndPeriod(Integer userId, Long from, Long to){
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();

        String sql = "SELECT o.id, o.item_id, o.amount, o.cart_id FROM orders o " +
                "JOIN carts c ON o.cart_id=c.id " +
                "WHERE " +
                "c.user_id=? AND " +
                "c.creation_time>=? AND " +
                "c.creation_time<=? " +
                "ORDER BY c.creation_time";

        NativeQuery quary = session.createNativeQuery(sql, Order.class);
        quary.setParameter(1, userId);
        quary.setParameter(2, from);
        quary.setParameter(3, to);

        List<Order> orders = quary.getResultList();

        session.getTransaction().commit();
        session.close();

        return orders;
    }
}
