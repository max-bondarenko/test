package org.max.spring.data.config.query.druid;

/**
 * Created by Maksym_Bondarenko on 3/3/2016.
 */
public interface HasParent<T> {
    T end();

    String build();
}
