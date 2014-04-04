package org.max.common;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.LoadEvent;
import org.hibernate.event.spi.LoadEventListener;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mbondarenko on 04.04.14.
 */
public class EventIntegrator implements Integrator {

    public static final Logger log = LoggerFactory.getLogger(EventIntegrator.class);

    @Override
    public void integrate(Configuration configuration, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        EventListenerRegistry service = serviceRegistry.getService(EventListenerRegistry.class);
        service.appendListeners(EventType.LOAD, new LoadEventListener() {
            @Override
            public void onLoad(LoadEvent event, LoadType loadType) throws HibernateException {
                log.debug("Event omitted: {} with loadType {}", event, loadType);
            }
        });

    }

    @Override
    public void integrate(MetadataImplementor metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {

    }

    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {

    }
}
