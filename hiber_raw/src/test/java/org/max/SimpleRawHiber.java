package org.max;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.max.entity.Author;
import org.max.entity.Book;
import org.max.entity.Hall;
import org.max.entity.Line;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by mbondarenko on 28.03.14.
 */
public class SimpleRawHiber {

    SessionFactory sf;

    @Before
    public void setUp() throws Exception {
        Configuration cfg = new Configuration().configure();

        cfg.addAnnotatedClass(Author.class);
        cfg.addAnnotatedClass(Book.class);

        sf = cfg.buildSessionFactory();


    }

    @Test
    public void test() throws Exception {

        Session s = sf.openSession();
        Query query = s.createQuery("from Book where id = 2" );
        List<Book> list = (List<Book>)query.list();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(new Integer(2), list.get(0).getId());
    }

    @Test
    public void testHall() throws Exception {
        Hall hall = new Hall();
        hall.setName("Left Hall");

        Session s = sf.openSession();
        Transaction tx = s.beginTransaction();

        System.out.println("is active :" + tx.isActive());
        s.save(hall);

        tx.commit();
        System.out.println("is active :" + tx.isActive());

        tx = s.beginTransaction();
        hall.setTraditionalNames(new HashSet<String>());
        hall.getTraditionalNames().add("lefthall");
        hall.getTraditionalNames().add("lifthall");

        tx.commit();
        s.close();

        s = sf.openSession();

        Hall loaded = (Hall)s.byId(Hall.class).load(1);

        assertEquals(2, loaded.getTraditionalNames().size());
    }
}
