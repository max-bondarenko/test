package org.max.spring.data.config.query;

import org.max.spring.data.config.QueryBackend;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

/**
 * Created by Maksym_Bondarenko on 2/19/2016.
 */
public class DruidMethodQuery extends DruidBaseQuery {

    private QueryMethod qm;

    public DruidMethodQuery(QueryBackend backend, RepositoryMetadata metadata, Method m) {
        super(backend, m.getReturnType());
        this.qm = new QueryMethod(m, metadata);
    }

    @Override
    public Object execute(Object[] parameters) {
        int length = parameters.length;
        Assert.isTrue(length == qm.getParameters().getNumberOfParameters(), "Wrong parameters count.");


        return null; //todo
    }

    @Override
    public QueryMethod getQueryMethod() {
        return qm;
    }
}
