package org.max.spring.data.config.query.template;

import org.max.spring.data.back.QueryBackend;
import org.max.spring.data.config.query.DruidBaseQuery;
import org.springframework.data.repository.query.QueryMethod;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Represents query backed by template
 * <p>
 * Created by Maksym_Bondarenko on 2/12/2016.
 */
public class DruidTemplateQuery extends DruidBaseQuery {

    private String templateName;
    private String ds;
    private PartTree tree;

    public DruidTemplateQuery(QueryBackend backend, PartTree tree, String templateName) {
        this(backend, tree, templateName, tree.getDataSource());

    }

    public DruidTemplateQuery(QueryBackend backend, PartTree tree, String templateName, String ds) {
        super(backend);
        this.tree = tree;
        this.templateName = templateName;
        this.ds = ds;
    }

    public String getTemplateName() {
        return templateName;
    }

    @Override
    public Object execute(Object[] parameters) {
        List<String> names = tree.getPropertyNames();
        Map<String, Object> pl = new HashMap<>();
        pl.put("dataSource", ds);

        String name = null;
        int i = 0;
        for (Iterator<String> iterator = names.iterator(); iterator.hasNext(); name = iterator.next(), i++) {
            pl.put(name, parameters[i]);
        }
        return backend.execute(templateName, pl);
    }

    @Override
    public QueryMethod getQueryMethod() {
        return null;
    }
}
