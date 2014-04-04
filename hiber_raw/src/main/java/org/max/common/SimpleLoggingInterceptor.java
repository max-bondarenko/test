package org.max.common;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by mbondarenko on 04.04.14.
 */
public class SimpleLoggingInterceptor extends EmptyInterceptor {

    public static final Logger log = LoggerFactory.getLogger(SimpleLoggingInterceptor.class);

    @Override
    public void afterTransactionBegin(Transaction tx) {
        log.debug("start TX: {} status {}", tx, tx.getLocalStatus());
    }

    @Override
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        log.debug("Entity id {} of {} ", id, entity.getClass());
        return false;
    }
}
