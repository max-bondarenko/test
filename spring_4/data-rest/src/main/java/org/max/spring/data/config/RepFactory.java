package org.max.spring.data.config;

import org.max.spring.data.back.QueryBackend;
import org.max.spring.data.config.annotations.DruidQuery;
import org.max.spring.data.config.query.DruidMethodQuery;
import org.max.spring.data.config.query.template.DruidTemplateQuery;
import org.max.spring.data.config.query.template.PartTree;
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
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Factory that actually responsible for creation of Repo or really backs Repo on behind.
 * Created by Maksym_Bondarenko on 2/11/2016.
 */
public class RepFactory extends RepositoryFactorySupport implements QueryLookupStrategy {

    private static final Logger log = LoggerFactory.getLogger(RepFactory.class);
    private static Collection<Class<?>> RAW_TYPES = new LinkedList<>(Arrays.asList(
            String.class,
            InputStream.class,
            Map.class,
            List.class
    ));
    private RestTemplate template;
    // may get from ctx by narrower types
    private QueryBackend backend;

    public void setTemplate(RestTemplate template) {
        this.template = template;
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
            PartTree tree = new PartTree(method.getName());
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

            return StringUtils.isEmpty(dataSource)
                    ? new DruidTemplateQuery(backend, tree, templateName, method.getReturnType())
                    : new DruidTemplateQuery(backend, tree, templateName, dataSource, method.getReturnType());
        } else {
            // not annotated method
            // should be parsed by method name //todo
            return new DruidMethodQuery(backend, metadata, method);
        }

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
