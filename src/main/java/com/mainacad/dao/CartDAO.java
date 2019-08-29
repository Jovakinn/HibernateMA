package com.mainacad.dao;

import com.mainacad.model.Cart;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class CartDAO {

    public static Cart save(Cart cart){
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();
        Integer id = (Integer) session.save(cart);
        cart.setId(id);

        session.getTransaction().commit();
        session.close();

        return cart;
    }

    public static Cart update(Cart cart){
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();
        session.update(cart);

        session.getTransaction().commit();
        session.close();

        return cart;
    }

    public static Cart findOneById(Integer id){
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();
        Cart cart = session.find(Cart.class, id);

        session.getTransaction().commit();
        session.close();

        return cart;
    }

    public static List<Cart> findByUser(Integer userId){
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();
        String sql = "SELECT * FROM carts WHERE user_id=?";

        NativeQuery quary = session.createNativeQuery(sql, Cart.class);
        quary.setParameter(1, userId);

        List<Cart> carts = quary.getResultList();

        session.getTransaction().commit();
        session.close();

        return carts;
    }

    public static List<Cart> findOpenCartByUser(Integer userId){
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();
        String sql = "SELECT * FROM carts WHERE closed=false AND user_id=?";

        NativeQuery quary = session.createNativeQuery(sql, Cart.class);
        quary.setParameter(1, userId);

        List<Cart> carts = quary.getResultList();

        session.getTransaction().commit();
        session.close();

        return carts;
    }

    public static Cart close(Integer cartId){
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.getTransaction().begin();
        String sql = "UPDATE carts SET closed=? WHERE id=?";

        NativeQuery query = session.createNativeQuery(sql, Cart.class);
        query.setParameter(1, true);
        query.setParameter(2, cartId);
        query.getResultList();

        Cart cart = findOneById(cartId);

        return cart;
    }
}