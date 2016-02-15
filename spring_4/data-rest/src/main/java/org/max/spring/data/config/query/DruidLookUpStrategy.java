package org.max.spring.data.config.query;

import org.max.spring.data.config.annotations.DruidQuery;
import org.max.spring.data.config.repository.GetRepository;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.util.StringUtils;

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

    private static Collection<Class<?>> RAW_TYPES = new LinkedList<>(Arrays.asList(
            String.class,
            InputStream.class,
            Map.class
    ));

    @Override
    public RepositoryQuery resolveQuery(Method method, RepositoryMetadata metadata, NamedQueries namedQueries) {
        DruidQuery annotation = AnnotationUtils.findAnnotation(method, DruidQuery.class);
        if (annotation != null) {
            checkRawType(annotation, method);
            return populateQuery(new DruidTemplateQuery(), annotation, method, metadata);
        } else if (method.getDeclaringClass().equals(GetRepository.class)) {
            return new DruidBaseQuery();
        }

        return null;
    }

    private RepositoryQuery populateQuery(DruidTemplateQuery query, DruidQuery methodAnn, Method method, RepositoryMetadata md) {
        String dataSource = getDataSource(methodAnn, method, md);
        query.setDataSource(dataSource);
        String templateName = methodAnn.templateName();
        if (StringUtils.isEmpty(templateName)) {
            templateName = method.getName();
        }
        query.setTemplateName(templateName);
        return query;
    }

    private String getDataSource(DruidQuery methodAnn, Method m, RepositoryMetadata md) {
        String dataSource = methodAnn.dataSource();
        if (StringUtils.isEmpty(dataSource)) {
            DruidQuery classAnn = AnnotationUtils.findAnnotation(md.getRepositoryInterface(), DruidQuery.class);
            if (classAnn == null) {
                throw new IllegalArgumentException("For empty dataSource on method " + m.getName()
                        + " must be DruidQuery annotation with dataSource set");
            }
            dataSource = classAnn.dataSource();
        }
        if (StringUtils.isEmpty(dataSource)) {
            throw new IllegalArgumentException("Empty dataSource name");
        }
        return dataSource;
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
