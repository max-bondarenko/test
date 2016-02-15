package org.max.spring.data.config.query;

import org.springframework.data.repository.query.QueryMethod;
import org.springframework.data.repository.query.RepositoryQuery;

/**
 * Created by Maksym_Bondarenko on 2/15/2016.
 */
public class DruidBaseQuery implements RepositoryQuery {
    @Override
    public Object execute(Object[] parameters) {

        return null; //todo
    }

    @Override
    public QueryMethod getQueryMethod() {
        return null; //todo
    }
}
