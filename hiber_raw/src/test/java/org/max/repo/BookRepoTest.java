package org.max.repo;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.core.IsCollectionContaining;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.stat.Statistics;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.max.common.AppFactory;
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
 * Created with IntelliJ IDEA.
 * User: add
 * Date: 29.03.14
 * Time: 11:48
 * To change this template use File | Settings | File Templates.
 */
public class BookRepoTest {

    private static final Logger log = LoggerFactory.getLogger(BookRepoTest.class);

    private SessionFactory sf;
    private Session session;
    BookRepo repo;

    public BookRepoTest() {
        sf = AppFactory.getInstance().getSessionFactory();
    }

    @Before
    public void setUp() throws Exception {
        session = sf.openSession();
        repo = new BookRepo(session);
    }

    @After
    public void tearDown() throws Exception {
        if (session.isOpen())
            session.close();
    }

    @Test
    public void testGetBook() throws Exception {
        BookRepo br = new BookRepo(session);
        Book book = br.getBookWithAllCollections(3);
        assertNotNull(book);

        Statistics statistics = sf.getStatistics();
        System.out.println(statistics.toString().replace(",", "\n"));
    }

    @Test
    public void testGetByAuthor() throws Exception {
        BookRepo r = new BookRepo(session);
        List<Book> withAuthor = r.getWithAuthor("Chad Fowler");

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
        log.info(load1.toString().replace(",", ",\n").replace("{", "{\n"));
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

    @Test
    public void testLoadAndSave() throws Throwable {
        Transaction tx = session.beginTransaction();
        Book b = repo.getBook(1000);
        assertNull(b);

        b = repo.loadBook(33);

        DateFormat df = new SimpleDateFormat("YYYY MM DD");
        String sourceDate = "2014 01 12";
        String title = "test title";
        String subtitle = "test subtitile";
        b.setPublicationDate(df.parse(sourceDate));
        b.setTitle(title);
        b.setSubtitle(subtitle);

        tx.commit();

        session.close();

        session = sf.openSession();

        repo = new BookRepo(session);
        Book b1 = repo.getBookWithAllCollections(33);
        assertNotNull(b1);
        assertEquals(df.parse(sourceDate), b1.getPublicationDate());
        assertEquals(title, b1.getTitle());
        assertEquals(subtitle, b1.getSubtitle());
    }
}
