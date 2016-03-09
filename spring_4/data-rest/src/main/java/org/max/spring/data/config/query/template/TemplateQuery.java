package org.max.spring.data.config.query.template;

import org.max.spring.data.back.QueryBackend;
import org.max.spring.data.config.query.DruidBaseQuery;
import org.springframework.data.repository.query.QueryMethod;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Represents query backed by template
 * <p/>
 * Created by Maksym_Bondarenko on 2/12/2016.
 */
public class TemplateQuery extends DruidBaseQuery {

    private static final String DATA_SOURCE = "dataSource";

    private String templateName;
    private String ds;
    private TemplatePartTree tree;

    public TemplateQuery(QueryBackend backend, TemplatePartTree tree, String templateName, Class responseType) {
        this(backend, tree, templateName, tree.getDataSource(), responseType);
    }

    public TemplateQuery(QueryBackend backend, TemplatePartTree tree, String templateName, String ds, Class responseType) {
        super(backend, responseType);
        this.tree = tree;
        this.templateName = templateName;
        this.ds = ds;
        validate();
    }

    private void validate() {
        HashSet<String> strings = new HashSet<String>(tree.getPropertyNames());
        strings.add(DATA_SOURCE);
        backend.validateTemplate(templateName, strings);
    }

    public String getTemplateName() {
        return templateName;
    }

    @Override
    public Object execute(Object[] parameters) {
        List<String> names = tree.getPropertyNames();
        Map<String, String> pl = new HashMap<>();
        pl.put(DATA_SOURCE, ds);
        int i = 0;
        for (String name : names) {
            pl.put(name, String.valueOf(parameters[i]));
            i++;
        }
        return backend.execute(templateName, pl, responseType);
    }

    @Override
    public QueryMethod getQueryMethod() {
        return null;
    }
}
