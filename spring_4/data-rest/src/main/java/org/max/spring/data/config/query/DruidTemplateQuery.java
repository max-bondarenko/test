package org.max.spring.data.config.query;

import org.max.spring.data.back.QueryBackend;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryMethod;

import java.lang.reflect.Method;

/**
 * Represents query backed by template
 *
 * Created by Maksym_Bondarenko on 2/12/2016.
 */
public class DruidTemplateQuery extends DruidBaseQuery {

    private QueryMethod queryMethod;
    private String templateName;
    private String dataSource;

    public DruidTemplateQuery(QueryBackend backend) {
        super(backend);
    }

    public DruidTemplateQuery(QueryBackend backend, Method method, RepositoryMetadata metadata) {
        super(backend);
        this.queryMethod = new QueryMethod(method, metadata);
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
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
