package org.max.data.config.query;

import org.max.data.config.annotations.DruidQuery;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.RepositoryQuery;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Maksym_Bondarenko on 2/12/2016.
 */
public class DruidLookUpStrategy implements QueryLookupStrategy {

    Collection<Class> RAW_TYPES = new LinkedList<>(Arrays.asList(
            String.class,
            InputStream.class,
            Map.class
    ));

    @Override
    public RepositoryQuery resolveQuery(Method method, RepositoryMetadata metadata, NamedQueries namedQueries) {
        DruidQuery annotation = AnnotationUtils.findAnnotation(method, DruidQuery.class);
        if (annotation != null) {
            checkRawType(annotation, method);

            return populateQuery(new DruidTemplateQuery(), annotation, method);

        }
        return null;
    }

    private RepositoryQuery populateQuery(DruidTemplateQuery query, DruidQuery annotation, Method m) {
        query.setDataSource(annotation.dataSource());
        query.setTemplateName(annotation.templateName());
        return query;
    }

    private void checkRawType(DruidQuery annotation, Method method) {
        Class<?> rType = method.getReturnType();
        if (annotation.isRaw()) {
            for (Class aClass : RAW_TYPES) {
                if (rType.isAssignableFrom(aClass)) {
                    return;
                }
            }
            throw new IllegalArgumentException();
        }
    }
}
