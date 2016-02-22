package org.max.spring.data.config.query;

import org.max.spring.data.back.QueryBackend;
import org.springframework.data.repository.query.RepositoryQuery;

/**
 * Created by Maksym_Bondarenko on 2/15/2016.
 */
public abstract class DruidBaseQuery implements RepositoryQuery {

    protected QueryBackend backend;

    public DruidBaseQuery(QueryBackend backend) {
        this.backend = backend;
    }

}
