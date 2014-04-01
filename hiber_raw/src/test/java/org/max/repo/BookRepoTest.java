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
import org.max.entity.Book;
import org.max.entity.Stack;
import org.max.common.AppFactory;

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
        System.out.println(load1.toString().replace(",", ",\n").replace("{", "{\n"));
    }

    @Test
    public void testLoadVSSave() throws Exception {
        Transaction tx = session.beginTransaction();
        Book bookWithAllCollections = repo.getBookWithAllCollections(8);
        Book book = repo.loadBook(8);

        assertSame(bookWithAllCollections,book);
        tx.rollback();

    }
}
