package org.max.spring.data.config.query.template;


import org.max.spring.data.config.QueryBackend;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Maksym_Bondarenko on 4/8/2016.
 */
public class CollectionTemplateQuery extends TemplateQuery {

    public CollectionTemplateQuery(QueryBackend backend, TemplatePartTree tree, String templateName, String ds, Class responseType) {
        super(backend, tree, templateName, ds, responseType);
    }


    public CollectionTemplateQuery(QueryBackend backend, TemplatePartTree tree, String templateName, Class responseType) {
        super(backend, tree, templateName, responseType);
    }

    @Override
    public Object execute(Object[] parameters) {
        Object[] execute = (Object[]) super.execute(parameters);
        return new ArrayList<>(Arrays.asList(execute));
    }
}
