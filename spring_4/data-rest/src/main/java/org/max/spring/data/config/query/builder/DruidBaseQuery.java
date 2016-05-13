package org.max.spring.data.config.query.builder;

import org.max.spring.data.config.QueryBackend;
import org.springframework.data.repository.query.RepositoryQuery;

/**
 * Created by Maksym_Bondarenko on 2/15/2016.
 */
public abstract class DruidBaseQuery implements RepositoryQuery {

    protected QueryBackend backend;
    protected Class<?> responseType;

    public DruidBaseQuery(QueryBackend backend, Class<?> responseType) {
        this.backend = backend;
        this.responseType = responseType;
    }

}
