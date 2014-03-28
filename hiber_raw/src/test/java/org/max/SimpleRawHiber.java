package org.max;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;

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
}
