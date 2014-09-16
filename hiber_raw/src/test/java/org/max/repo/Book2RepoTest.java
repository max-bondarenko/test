package org.max.repo;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.core.IsCollectionContaining;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.stat.Statistics;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.max.common.AppFactory;
import org.max.common.SimpleLoggingInterceptor;
import org.max.entity.Book;
import org.max.entity.Stack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by mbondarenko on 30.05.14.
 */

public class Book2RepoTest {

    private static final Logger log = LoggerFactory.getLogger(Book2RepoTest.class);

    private SessionFactory sf;
    private Session session;
    Book2Repo repo;

    public Book2RepoTest() {
        Configuration cfg = new Configuration();
        cfg.setProperty("hibernate.current_session_context_class", "thread");
        cfg.configure();

        cfg.setInterceptor(new SimpleLoggingInterceptor());


        sf = AppFactory.getInstance().getSessionFactory();
        repo = new Book2Repo(sf);
    }

    @Before
    public void setUp() throws Exception {
        session = sf.getCurrentSession();
    }

    @After
    public void tearDown() throws Exception {
        if (session.isOpen())
            session.close();
    }

    @Test
    public void testGetBook() throws Exception {

        log.debug("sess {}", session);
        Book book = repo.getBookWithAllCollections(3);
        assertNotNull(book);

        Statistics statistics = sf.getStatistics();
        System.out.println(statistics.toString().replace(",", "\n"));

    }

    @Test
    public void testGetByAuthor() throws Exception {
        session.beginTransaction();
        List<Book> withAuthor = repo.getWithAuthor("Chad Fowler");

        assertNotNull(withAuthor);
        assertEquals(2, withAuthor.size());

        assertThat(withAuthor, new IsCollectionContaining<Book>(new BaseMatcher<Book>() {
            @Override
            public boolean matches(Object item) {
                if (item instanceof Book) {
                    boolean equals = ((Book) item).getTitle().equals("Rails Recipes");
                    return equals;
                } else
                    return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(" Rails Recipes should be");
            }
        }));
    }

    @Test
    public void testUpdate() throws Exception {
        Stack firstStack = new Stack();
        firstStack.setName("Left-side stack");

        Set<Book> set = new HashSet<Book>();
        firstStack.setBooks(set);
        IdentifierLoadAccess idLoadBook = session.byId(Book.class);
        Book load = (Book) idLoadBook.load(38);
        set.add(load);
        load = (Book) idLoadBook.load(39);
        set.add(load);

        session.save(firstStack);
        session.flush();

        Object load1 = session.byId(Stack.class).load(1);

        assertNotNull(load1);
        System.out.println(load1.toString().replace(",", ",\n").replace("{", "{\n"));
    }

    @Test
    public void testLoadVSSave() throws Exception {
        Transaction tx = session.beginTransaction();
        Book bookWithAllCollections = repo.getBookWithAllCollections(8);
        Book book = repo.loadBook(8);

        assertSame(bookWithAllCollections, book);
        tx.rollback();
    }

    @Test
    public void testGet() throws Exception {
        Book bookWithAllCollections = repo.getBookWithAllCollections(1000);
        assertNull(bookWithAllCollections);
    }

    @Test(expected = org.hibernate.ObjectNotFoundException.class)
    public void testLoad() throws Throwable {
        Book bookWithAllCollections = repo.loadBook(1000);
        assertNotNull(bookWithAllCollections);
        try {

            log.info("for newly load got id:{}", bookWithAllCollections.getId());
            assertNotNull(bookWithAllCollections.getTitle());
        } catch (Throwable e) {
            log.warn("got " + e.getClass());
            throw e;
        }

    }

    @Test(expected = org.hibernate.HibernateException.class)
    public void testLoadWithoutTx() throws Exception {
        Book b = repo.loadBook(38);
        assertNotNull(b);
    }

    @Test
    public void testLoadWithTx() throws Throwable {
        Transaction tx = session.beginTransaction();
        Book b = repo.loadBook(38);
        assertNotNull(b);
        log.info("for newly load got id:{}", b.getId());
        log.info("contains : {}", session.contains(b));
    }

    @Test(expected = org.hibernate.HibernateException.class)
    public void testLoadAndSaveWithoutTx() throws Exception {
        Transaction tx = session.beginTransaction();
        Book b = repo.getBookWithAllCollections(1000);
        assertNull(b);
        log.info("contains : {}", session.contains(b));

        DateFormat df = new SimpleDateFormat("YYYY MM DD");
        String sourceDate = "2014 01 12";
        String title = "test title";
        String subtitle = "test subtitile";
        b = new Book();
        b.setPublicationDate(df.parse(sourceDate));
        b.setTitle(title);
        b.setSubtitle(subtitle);
        tx.commit();

        Book b1 = repo.getBookWithAllCollections(b.getId());
        assertNotNull(b1);
        assertEquals(df.parse(sourceDate), b1.getPublicationDate());
        assertEquals(title, b1.getTitle());
        assertEquals(subtitle, b1.getSubtitle());

    }

    @Test(expected = org.hibernate.SessionException.class)
    public void testLoadAndSaveWithTx() throws Exception {
        Transaction tx = session.beginTransaction();
        Book b = repo.getBookWithAllCollections(1000);
        assertNull(b);
        log.info("contains : {}", session.contains(b));

        DateFormat df = new SimpleDateFormat("YYYY MM DD");
        String sourceDate = "2014 01 12";
        String title = "test title";
        String subtitle = "test subtitile";
        b = new Book();
        b.setPublicationDate(df.parse(sourceDate));
        b.setTitle(title);
        b.setSubtitle(subtitle);
        tx.commit();

        session.beginTransaction();


    }
}
