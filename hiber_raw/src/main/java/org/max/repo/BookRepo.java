package org.max.repo;

import org.hibernate.Session;
import org.max.entity.Book;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: add
 * Date: 29.03.14
 * Time: 11:20
 * To change this template use File | Settings | File Templates.
 */
public class BookRepo {

    private Session session;

    public BookRepo(Session session) {
        this.session = session;
    }

    public Book getBookWithAllCollections(Integer id) {
        return (Book) session.get(Book.class, id);
    }
    public Book loadBook(Integer id) {
        return (Book) session.load(Book.class, id);
    }

    public List<Book> getWithAuthor(String authorName){
        List list = session.createQuery("from Book as b join fetch b.authors as a " +
                "where  a.name = :author")
                .setString("author",authorName)
                .list();
        return list;
    }

    public Book getWithAuthor(Integer id) {
        List<Book> list = session.createQuery("from Book as b join fetch b.authors as a " +
                "where  a.id = :id")
                .setInteger("id", id)
                .list();
        return list.get(0);
    }

    public void detach(Book b){
        session.evict(b);
    }

    public void delete(Book b){
        session.delete(b);
    }

    public void delete(Integer bookId){
        Book b = new Book();
        b.setId(bookId);
        session.delete(b);
    }

}
