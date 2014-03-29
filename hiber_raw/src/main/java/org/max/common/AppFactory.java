package org.max.common;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.BootstrapServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.max.Author;
import org.max.Book;

/**
 * Created with IntelliJ IDEA.
 * User: add
 * Date: 29.03.14
 * Time: 11:36
 * To change this template use File | Settings | File Templates.
 */
public class AppFactory {
    private static AppFactory ourInstance = new AppFactory();

    private Configuration cfg;
    private SessionFactory sf;


    public static AppFactory getInstance() {
        return ourInstance;
    }

    private AppFactory() {
        Configuration cfg = new Configuration();
        cfg.configure();
        cfg.addAnnotatedClass(Book.class);
        cfg.addAnnotatedClass(Author.class);
        this.cfg = cfg;
    }

    public SessionFactory getSessionFactory(){
        ServiceRegistryBuilder srb = new ServiceRegistryBuilder();
        srb.applySettings(cfg.getProperties());

        SessionFactory sessionFactory = cfg.buildSessionFactory(srb.buildServiceRegistry());
        return sessionFactory;
    }



}
