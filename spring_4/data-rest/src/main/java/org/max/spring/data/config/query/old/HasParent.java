package org.max.spring.data.config.query.old;

/**
 * Created by Maksym_Bondarenko on 3/3/2016.
 */
public interface HasParent<T> {
    T end();

    String build();
}
