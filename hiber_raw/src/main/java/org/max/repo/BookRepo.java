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

    public Book getBook(Integer id) {
        return (Book) session.get(Book.class, id);
    }

    public List<Book> getWithAuthor(String authorName){
        List list = session.createQuery("from Book as b join fetch b.authors as a " +
                "where  a.name = :author")
                .setString("author",authorName)
                .list();

        return list;
    }

}
