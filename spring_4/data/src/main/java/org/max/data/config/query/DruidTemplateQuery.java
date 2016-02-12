package org.max.data.config.query;

import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.data.repository.query.RepositoryQuery;

import java.lang.reflect.Method;

/**
* Created by Maksym_Bondarenko on 2/12/2016.
*/
public class DruidTemplateQuery implements RepositoryQuery {

    private QueryMethod queryMethod;
    private Method method;
    private RepositoryMetadata metadata;
    private NamedQueries namedQueries;

    public DruidTemplateQuery() {

    }

    public DruidTemplateQuery(Method method, RepositoryMetadata metadata, NamedQueries namedQueries) {
        this.queryMethod = new QueryMethod(method, metadata);


        this.metadata = metadata;
        this.namedQueries = namedQueries;
        this.method = method;

    }

    @Override
    public Object execute(Object[] parameters) {
        return null;
    }

    @Override
    public QueryMethod getQueryMethod() {


        return this.queryMethod;
    }
}
