package org.max.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SessionFactoryObserver;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.internal.StandardServiceRegistryImpl;
import org.max.Author;
import org.max.Book;
import org.max.common.AppFactory;

/**
 * Created with IntelliJ IDEA.
 * User: add
 * Date: 29.03.14
 * Time: 11:20
 * To change this template use File | Settings | File Templates.
 */
public class BookService {

    private SessionFactory sf = AppFactory.getInstance().getSessionFactory();

    public Book getBook(Integer id) {
        Session s = sf.openSession();
        try {
            return (Book) s.get(Book.class, id);
        } finally {
            s.close();
        }

    }

}
