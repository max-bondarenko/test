package org.max.repo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.max.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: add
 * Date: 29.03.14
 * Time: 11:20
 * To change this template use File | Settings | File Templates.
 */
public class Book2Repo {

    public static final Logger log = LoggerFactory.getLogger(Book2Repo.class);

    private SessionFactory sessionFactory;

    public Book2Repo(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public Book getBookWithAllCollections(Integer id) {
        Session cs = sessionFactory.getCurrentSession();
        log.debug("session: {}", cs);
        return (Book) cs.get(Book.class, id);
    }

    public Book loadBook(Integer id) {
        return (Book) sessionFactory.getCurrentSession().load(Book.class, id);
    }

    public List<Book> getWithAuthor(String authorName) {
        List list = sessionFactory.getCurrentSession().createQuery("from Book as b join fetch b.authors as a " +
                "where  a.name = :author")
                .setString("author", authorName)
                .list();
        return list;
    }

    public Book getWithAuthor(Integer id) {
        List<Book> list = sessionFactory.getCurrentSession().createQuery("from Book as b join fetch b.authors as a " +
                "where  a.id = :id")
                .setInteger("id", id)
                .list();
        return list.get(0);
    }

    public void detach(Book b) {
        sessionFactory.getCurrentSession().evict(b);
    }

    public void delete(Book b) {
        sessionFactory.getCurrentSession().delete(b);
    }

    public void delete(Integer bookId) {
        Book b = new Book();
        b.setId(bookId);
        sessionFactory.getCurrentSession().delete(b);
    }

}
