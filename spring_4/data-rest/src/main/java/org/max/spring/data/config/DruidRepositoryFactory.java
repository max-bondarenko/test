package org.max.spring.data.config;

import org.max.spring.data.config.annotations.DruidQuery;
import org.max.spring.data.config.query.template.CollectionTemplateQuery;
import org.max.spring.data.config.query.template.TemplatePartTree;
import org.max.spring.data.config.query.template.TemplateQuery;
import org.max.spring.data.config.repository.DefaultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Factory that actually responsible for creation of Repo or really backs Repo on behind.
 * Created by Maksym_Bondarenko on 2/11/2016.
 */
public class DruidRepositoryFactory extends RepositoryFactorySupport implements QueryLookupStrategy {

    private static final Logger log = LoggerFactory.getLogger(DruidRepositoryFactory.class);
    // may get from ctx by narrower types
    private QueryBackend backend;

    @SuppressWarnings("unchecked")
    public static <T> Class<? extends T[]> getArrayClass(Class<T> clazz) {
        return (Class<? extends T[]>) Array.newInstance(clazz, 0).getClass();
    }

    @Override
    protected void validate(RepositoryMetadata repositoryMetadata) {
        log.info("validate it: {}", repositoryMetadata);
    }

    @Override
    protected QueryLookupStrategy getQueryLookupStrategy(QueryLookupStrategy.Key key) {
        return this;
    }

    @Override
    public <T, ID extends Serializable> EntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        throw new UnsupportedOperationException("getEntityInfo not supported for this type of Repo");
    }

    @Override
    protected Object getTargetRepository(RepositoryMetadata md) {
        Class<?> domainType = md.getDomainType();
        DefaultRepository df = new DefaultRepository(domainType);
        df.setBackend(backend);
        return df;
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return DefaultRepository.class;
    }

    public void setBackend(QueryBackend backend) {
        this.backend = backend;
    }

    @Override
    public RepositoryQuery resolveQuery(Method method, RepositoryMetadata metadata, NamedQueries namedQueries) {
        DruidQuery annotation = AnnotationUtils.findAnnotation(method, DruidQuery.class);
        if (annotation != null) {
            TemplatePartTree tree = new TemplatePartTree(method.getName());
            DruidQuery methodAnn = AnnotationUtils.findAnnotation(method, DruidQuery.class);
            //get data source from parts
            String dataSource = tree.getDataSource();
            if (StringUtils.isEmpty(dataSource)) {
                dataSource = getDataSource(methodAnn, method, metadata);
            }
            String templateName = methodAnn.templateName();
            if (StringUtils.isEmpty(templateName)) {
                templateName = method.getName();
            }
            //fot collections
            Class<?> returnType = method.getReturnType();
            if (Collection.class.isAssignableFrom(returnType)) {
                Class<?> declaringClass = method.getDeclaringClass();
                Type type = declaringClass.getGenericInterfaces()[0];
                if (type instanceof ParameterizedType) {
                    ParameterizedType y = (ParameterizedType) type;
                    returnType = getArrayClass((Class) y.getActualTypeArguments()[0]);
                    return StringUtils.isEmpty(dataSource)
                            ? new CollectionTemplateQuery(backend, tree, templateName, returnType)
                            : new CollectionTemplateQuery(backend, tree, templateName, dataSource, returnType);
                }
            }
            return StringUtils.isEmpty(dataSource)
                    ? new TemplateQuery(backend, tree, templateName, returnType)
                    : new TemplateQuery(backend, tree, templateName, dataSource, returnType);
        }
        throw new IllegalArgumentException("Method must be annotated with DruidQuery annotation");

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
}
