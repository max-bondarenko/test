package org.max.common;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

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
        cfg.setInterceptor(new SimpleLoggingInterceptor());
        this.cfg = cfg;
    }

    public SessionFactory getSessionFactory() {
        if (sf == null) {
            ServiceRegistryBuilder srb = new ServiceRegistryBuilder();
            srb.applySettings(cfg.getProperties());
            sf = cfg.buildSessionFactory(srb.buildServiceRegistry());
        }
        return sf;
    }

    /*
      BootstrapServiceRegistryBuilder bsrb = new BootstrapServiceRegistryBuilder();
            ServiceRegistryBuilder srb = new ServiceRegistryBuilder();
            bsrb.with(new EventIntegrator());

            BootstrapServiceRegistry built = srb.build();


            srb.applySettings(cfg.getProperties());

            sf = cfg.buildSessionFactory(serviceRegistry);
     */


}
