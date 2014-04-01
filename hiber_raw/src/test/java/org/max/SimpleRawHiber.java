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

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;

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
        Query query = s.createQuery("from Book where id = 2");
        List<Book> list = (List<Book>) query.list();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(new Integer(2), list.get(0).getId());


    }

    @Test
    public void testDirtyCheck() throws Exception {
        Session s = sf.openSession();
        Transaction tx = s.beginTransaction();
        Book thirdBook = (Book) s.get(Book.class, 3);
        String subtitle = thirdBook.getSubtitle();
        final String subtitle1 = "Different subtitle for test";
        thirdBook.setSubtitle(subtitle1);
        tx.commit();
        s.close();

        s = sf.openSession();

        Book book = (Book) s.get(Book.class, 3);
        assertNotNull(book);
        assertNotSame(book, thirdBook);

        s.close();
        assertNotNull(book);
        assertEquals(book.getId(), thirdBook.getId());
        assertEquals(subtitle1, book.getSubtitle());
    }

    @Test
    public void testDirtyCheckWithoutTx() throws Exception {
        //dirty check don`t works without transaction
        Session s = sf.openSession();
        Book thirdBook = (Book) s.get(Book.class, 8);
        String subtitle = thirdBook.getSubtitle();
        final String subtitle1 = "Different subtitle for test";
        thirdBook.setSubtitle(subtitle1);

        s.close();

        s = sf.openSession();

        Book book = (Book) s.get(Book.class, 8);
        assertNotNull(book);
        assertNotSame(book, thirdBook);

        s.close();
        assertNotNull(book);
        assertEquals(book.getId(), thirdBook.getId());
        assertEquals(subtitle, book.getSubtitle());
    }
}
