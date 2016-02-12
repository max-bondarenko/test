package org.max.data.config.query;

import org.max.data.config.annotations.DruidQuery;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.RepositoryQuery;

import java.lang.reflect.Method;

/**
 * Created by Maksym_Bondarenko on 2/12/2016.
 */
public class DruidLookUpStrategy implements QueryLookupStrategy {
    @Override
    public RepositoryQuery resolveQuery(Method method, RepositoryMetadata metadata, NamedQueries namedQueries) {
        if (isQueryAnnotated(method, metadata)) {
            return new DruidTemplateQuery();
        }

        return null;
    }

    private boolean isQueryAnnotated(Method method, RepositoryMetadata metadata) {
        DruidQuery annotation = AnnotationUtils.findAnnotation(method, DruidQuery.class);
        return annotation != null;
    }
}
