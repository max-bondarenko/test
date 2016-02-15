package org.max.data.config.query;

import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.data.repository.query.RepositoryQuery;

import java.lang.reflect.Method;

/**
 * Represents query backed by template
 *
 * Created by Maksym_Bondarenko on 2/12/2016.
 */
public class DruidTemplateQuery implements RepositoryQuery {

    private QueryMethod queryMethod;
    private String templateName;
    private String dataSource;

    public DruidTemplateQuery() {
    }

    public DruidTemplateQuery(Method method, RepositoryMetadata metadata) {
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
